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
			/*name=*/"GetFileDemo(jarUpdater)",
			/*clientMetaFile=*/new File("libs/client.json"),
			/*serverMetaURI=*/URI.create(GetFile.LATEST_JAR_URL),
			/*showProgress=*/true, /*ignoreErrors=*/false);
		BackupManager jarUpdaterBak = jarUpdater.getBackupManager();
		jarUpdaterBak.backup();

		// Get airbus and boeing plane metadata for planes demo.
		GetFile airbus = new GetFile(
			/*name=*/"GetFileDemo(airbus)",
			/*clientMetaFile=*/new File("data/airbus-client.json"),
			/*serverMetaURI=*/URI.create(
				"https://g-c662a6.a78b8.36fe.data.globus.org/getfile-demo/planes/airbus.json"),
			/*showProgress=*/true, /*ignoreErrors=*/false);
		BackupManager airbusBak = airbus.getBackupManager();
		airbusBak.backup();

		GetFile boeing = new GetFile(
			/*name=*/"GetFileDemo(boeing)",
			/*clientMetaFile=*/new File("data/boeing-client.json"),
			/*serverMetaURI=*/URI.create(
				"https://g-c662a6.a78b8.36fe.data.globus.org/getfile-demo/planes/boeing.json"),
			/*showProgress=*/true, /*ignoreErrors=*/false);
		BackupManager boeingBak = boeing.getBackupManager();
		boeingBak.backup();

		System.out.println("Update cache and backed up local data");
		myWait();

		jarUpdater.updateFile("getfile-all");
		airbus.updateAll();
		boeing.updateFile("737 Data");
		boeing.updateFile("767.md");
		System.out.println("Updated from server");
		myWait();
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
}

