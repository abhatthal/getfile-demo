# GetFile Demo 1

This demo shows
* Connecting to a local server
* 1 GetFile instance
* 2 BackupManagers
* Demonstrates multiple snapshots
* Existing outdated client files
* Updating all files and updating files individually

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

Navigate to the client files to watch the updates and rollbacks occur.

