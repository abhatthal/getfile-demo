import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.lang.Thread;
import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
			/*showProgress=*/true);
		BackupManager jarUpdaterBak = jarUpdater.getBackupManager();
		jarUpdaterBak.backup();

		// Get airbus and boeing plane metadata for planes demo.
		GetFile airbus = new GetFile(
			/*name=*/"GetFileDemo(airbus)",
			/*clientMetaFile=*/new File("data/airbus-client.json"),
			/*serverMetaURIs=*/List.of(
                    URI.create("https://opensha.scec.org/getfile-demo/planes/airbus.json"),  // OpenSHA
                    URI.create("https://g-3a9041.a78b8.36fe.data.globus.org/getfile-demo/planes/airbus.json")), // CARC /project2/scec_608/public
			/*showProgress=*/true);
		BackupManager airbusBak = airbus.getBackupManager();
		airbusBak.backup();

		GetFile boeing = new GetFile(
			/*name=*/"GetFileDemo(boeing)",
			/*clientMetaFile=*/new File("data/boeing-client.json"),
            /*serverMetaURIs=*/List.of(
                URI.create("https://g-3a9041.a78b8.36fe.data.globus.org/getfile-demo/planes/boeing.json"),  // CARC /project2/scec_608/public
                URI.create("https://opensha.scec.org/getfile-demo/planes/boeing.json")), // OpenSHA
			/*showProgress=*/true);
		BackupManager boeingBak = boeing.getBackupManager();
		boeingBak.backup();

		System.out.println("Update cache and backed up local data");
		myWait();

		CompletableFuture<?> future1 = jarUpdater.updateFile("getfile-all");
		CompletableFuture<?> future2 = airbus.updateAll();
		CompletableFuture<?> future3 = boeing.updateFile("737 Data");
		CompletableFuture<?> future4 = boeing.updateFile("767.md");

		CompletableFuture<?> allFutures = CompletableFuture.allOf(
				future1, future2, future3, future4);
		allFutures.join();
		System.out.println("Updated from server");
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

