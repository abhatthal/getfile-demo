# GetFile Demo 2
This demo will show how rollbacks can persist across sessions by using
two BackupManagers to toggle the state.

This demo shows
* no initial data or local metadata.
* ability to generate the metadata over updating
* creation of client data directory
* shows how rollbacks still result in empty metadata files (Expected behavior)
* doesn't create backup files for files that didn't exist (Expected behavior)
* Uses 1 GetFile instance to connect to one local server

## Terminal 1: Run server
```
cd server
python3 -m http.server 8088
```
## Terminal 2: Run GetFileDemo
```
cd client
javac -cp .:libs/getfile-all.jar GetFileDemo.java
java -cp .:libs/getfile-all.jar GetFileDemo
```
Rerun GetFileDemo multiple times to observe the toggling between updated and
not updated using BackupManagers.

After you are done observing the toggling, run `clean.sh` to revert the
demonstration to the initial state of not updated and without any backup files.

