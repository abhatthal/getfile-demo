import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.lang.Thread;
import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

public class GetFileDemo {
	static List<File> updated = new ArrayList<File>();
	static List<File> unchanged = new ArrayList<File>();

	public static void main(String[] Args) {
		// Get the latest version of the code. Not loaded in this session.
		GetFile jarUpdater = new GetFile(
			/*clientMetaFile=*/new File("libs/client.json"),
			/*serverMetaURI=*/URI.create(GetFile.LATEST_JAR_URL));
		BackupManager jarUpdaterBak = jarUpdater.getBackupManager();
		jarUpdaterBak.backup();

		// Get airbus and boeing plane metadata for planes demo.
		GetFile airbus = new GetFile(
			/*clientMetaFile=*/new File("airbus-client.json"),
			/*serverMetaURI=*/URI.create(
				"https://g-c662a6.a78b8.36fe.data.globus.org/getfile-demo/planes/airbus.json"));
		BackupManager airbusBak = airbus.getBackupManager();
		airbusBak.backup();

		GetFile boeing = new GetFile(
			/*clientMetaFile=*/new File("boeing-client.json"),
			/*serverMetaURI=*/URI.create(
				"https://g-c662a6.a78b8.36fe.data.globus.org/getfile-demo/planes/boeing.json"));
		BackupManager boeingBak = boeing.getBackupManager();
		boeingBak.backup();

		System.out.println("Update cache and backed up local data");
		myWait();

		updateFileWrapper(jarUpdater, "getfile-all");
		updateAllWrapper(airbus);
		updateFileWrapper(boeing, "737 Data");
		updateFileWrapper(boeing, "767.md");
		System.out.println("Updated from server");
		myWait();

		jarUpdaterBak.rollback();
		airbusBak.rollback();
		boeingBak.rollback();
		System.out.println("Rolled back to backup");
		myWait();

		System.out.print("Updated: ");
		printFiles(updated);
		System.out.print("\nUnchanged: ");
		printFiles(unchanged);
	}

	public static void myWait() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printFiles(List<File> files) {
		for (File f : files) {
			System.out.println(f.getPath());			
		}
	}

	// These wrappers allow us to track all updated and unchanged files across
	// GetFile instances. In a larger example, we may create a GetFileWrapper class
	// and store the updated/unchanged lists inside.

	// Updates the file and adds the resulting File to a list of updated/unchanged files
	public static void updateFileWrapper(GetFile instance, String fileKey) {
		Pair<Boolean, File> pair = instance.updateFile(fileKey);
		if (pair.getLeft()) {
			updated.add(pair.getRight());
		} else {
			unchanged.add(pair.getRight());
		}
	}
	// Updates all the files in the given instance and populates the updated/unchanged files
	public static void updateAllWrapper(GetFile instance) {
		Map<String, List<File>> mapping = instance.updateAll();
		updated.addAll(mapping.get("updated"));
		unchanged.addAll(mapping.get("unchanged"));			
	}
	
}

