import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.lang.Thread;
import java.io.File;
import java.net.URI;

public class GetFileDemo {
	public static void main(String[] Args) {
		GetFile gf = new GetFile(
			/*clientMetaFile=*/new File("getfile.json"),
			/*serverMetaURI=*/URI.create("http://localhost:8088/meta.json"));
		BackupManager bm1 = gf.getBackupManager("bm1");
		BackupManager bm2 = gf.getBackupManager("bm2");

		/**
		 * Expected behavior:
		 *  Create bm1 and bm2
		 *  roll to bm1 and now only has bm2
		 *  roll to bm2 and now only has bm1
		 *  roll to bm1 and now only has bm2 ... toggles state forever
		 **/

		if (!bm1.backupExists() && !bm2.backupExists()) {
			System.out.println("Update files with pre and post backups.");
			bm1.backup();
			gf.updateAll();
			bm2.backup();
		} else if (bm1.backupExists() && !bm2.backupExists()) {
			System.out.println("Rollback to 1 and prepare for future roll to 2");
			bm2.backup();
			bm1.rollback();
		} else if (!bm1.backupExists() && bm2.backupExists()) {
			System.out.println("Rollback to 2 and prepare for future roll to 1");
			bm1.backup();
			bm2.rollback();
		} else {
			System.out.println("Start toggle with just rolling 1");
			bm1.rollback();
		}
	}
}

