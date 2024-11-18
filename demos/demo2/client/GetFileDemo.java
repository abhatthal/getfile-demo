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

		if (bm1.backupExists() && !bm2.backupExists()) {
			System.out.println("Backup updated state and rollback to initial state");
			bm2.backup();
			bm1.rollback();
		} else if (!bm1.backupExists() && bm2.backupExists()) {
			System.out.println("Backup initial state and rollback to updated state");
			bm1.backup();
			bm2.rollback();
		} else {
			System.out.println("Backup initial state and update");
			bm1.backup();
			gf.updateAll();
		}
	}
}

