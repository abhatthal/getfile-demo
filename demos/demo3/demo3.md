# GetFile Demo 3

Demonstrate how GetFile can update itself.

Instead of using the shared libs/ symlink, we have a custom getfile-all.jar
with a message embedded inside the updateAll method.

We will demonstrate
* the message present in the outdated library
* gone after updating
* back again after rolling back

Libraries are difficult to load dynamically at runtime.
Rerunning the application with toggle logic (like in demo2) will allow us to
demonstrate the changes.

```
cd client
java -cp .:libs/getfile-all.jar GetFileDemo
```

There is no need to start a local server here as we are connecting to GitHub.
Alternate running the Java application to toggle the old and new versions.
The message "Hello! I'm an outdated JAR invoked inside GetFile.updateAll" is
seen when running the old version. You don't need to recompile GetFileDemo,
but the invocation requires specifying the classpath.

```
	System.out.println("\u001B[41m"
			+ "Hello! I'm an outdated JAR invoked inside GetFile.updateAll"
			+ "\033[0m");
```

After done with this demo, use the `clean.sh` script to revert to initial state.

