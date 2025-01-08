import org.scec.getfile.GetFile;
import org.scec.getfile.BackupManager;

import java.lang.Thread;
import java.io.File;
import java.net.URI;

public class GetFileDemo {
	public static void main(String[] Args) {
		GetFile gf = new GetFile(
			/*name=*/"GetFileDemo",
			/*clientMetaFile=*/new File("getfile.json"),
			/*serverMetaURI=*/URI.create("http://localhost:8088/meta.json"),
			/*showProgress=*/true);
		BackupManager bm = gf.getBackupManager();
		BackupManager bm1 = gf.getBackupManager("1"); // New instance
		BackupManager bm2 = gf.getBackupManager(); // Same instance as bm

		bm1.backup();
		gf.updateAll().thenRun(() -> {
			bm.backup();
			System.out.println("Updated");
			myWait();

			bm1.rollback();
			System.out.println("Rolled back to before update.");
			bm1.backup();
			myWait();

			gf.updateFile("file2").thenRun(() -> {
				System.out.println("Update just file2");
				myWait();

				bm2.rollback();
				System.out.println("Roll back to after update.");
				myWait();
				
				System.out.println("Rolled back to before update.");
				bm1.rollback();
				System.out.println("Delete server cache");
				File serverMetaCache = new File("meta.json");
				if (serverMetaCache.exists()) {
					serverMetaCache.delete();
				}
			});
		});
	}

	public static void myWait() {
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

