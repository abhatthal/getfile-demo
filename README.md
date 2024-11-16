# GetFile Demo
This demo shows how GetFile can be used to update files and manage snapshots.
Only GetFile and BackupManager are available for use outside the package.

GetFile source: https://github.com/abhatthal/getfile

## Terminal 1: Run server
```
cd getfile-demo/server
python3 -m http.server 8088
```
## Terminal 2: Run GetFileDemo
```
cd getfile-demo
javac -cp lib/getfile-all.jar GetFileDemo.java
java -cp .:lib/getfile-all.jar GetFileDemo
```
