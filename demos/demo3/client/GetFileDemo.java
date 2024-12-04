import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.lang.Thread;
import java.io.File;
import java.net.URI;

public class GetFileDemo {
	public static void main(String[] Args) {
		GetFile gf = new GetFile(
			/*name=*/"GetFileDemo",
			/*clientMetaFile=*/new File("libs/client.json"),
			/*serverMetaURI=*/URI.create(GetFile.LATEST_JAR_URL),
			/*showProgress=*/true, /*ignoreErrors=*/false);
		BackupManager bm1 = gf.getBackupManager("bm1");
		BackupManager bm2 = gf.getBackupManager("bm2");

		if (bm1.backupExists() && !bm2.backupExists()) {
			System.out.println("Rollback to 1 and prepare for future roll to 2");
			bm2.backup();
			System.out.println("Version on GitHub is without the message.");
			gf.updateAll();
			bm1.rollback();
		} else if (!bm1.backupExists() && bm2.backupExists()) {
			System.out.println("Rollback to 2 and prepare for future roll to 1");
			bm1.backup();
			System.out.println("Old version should have special message.");
			gf.updateAll();
			bm2.rollback();
		} else {
			bm1.backup();
			System.out.println("Old version should have special message.");
			gf.updateAll();
		}
	}
}

