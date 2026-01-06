# GetFile Demo 4

This demo will use three GetFile instances
1. Check for latest version of GetFile and use our cached version
2. Get latest Boeing airplane data
3. Get latest Airbus airplane data

We will demonstrate
* How a single project can use multiple GetFile instances
* How the output of updateAll and updateFile can be read
* Reading data off CARC, the OpenSHA server, and GitHub

```
cd client
java -cp .:libs/getfile-all.jar GetFileDemo.java
```

After done with this demo, use the `clean.sh` script to revert to initial state.

