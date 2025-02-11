import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GetFileDemo {
	public static void main(String[] Args) throws IOException {
		GetFile gf = new GetFile(
			/*name=*/"GetFileDemo",
			/*clientMetaFile=*/new File("libs/client.json"),
			/*serverMetaURI=*/URI.create(GetFile.LATEST_JAR_URL),
			/*showProgress=*/true);
		BackupManager bm1 = gf.getBackupManager("bm1");
		BackupManager bm2 = gf.getBackupManager("bm2");

		if (bm1.backupExists() && !bm2.backupExists()) {
			System.out.println("Rollback to 1 and prepare for future roll to 2");
			bm2.backup();
			System.out.println("Version on GitHub is without the message.");
			gf.updateAll().join();
			bm1.rollback();
		} else if (!bm1.backupExists() && bm2.backupExists()) {
			System.out.println("Rollback to 2 and prepare for future roll to 1");
			bm1.backup();
			System.out.println("Old version should have special message.");
			gf.updateAll().join();
			bm2.rollback();
		} else {
			System.out.println("Backing up");
			bm1.backup();
			System.out.println("Old version should have special message.");
			gf.updateAll().join();
		}
	}
}

