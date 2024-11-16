# GetFile Demo 3

Demonstrate how GetFile can update itself.

This demo doesn't use the shared libs/ resource. Instead it has its own libs/
directory with an outdated version of getfile-all.jar. This version has backupExists
hidden as package-private, and therefore is not accessible. We will update to the
latest version available on GitHub and verify that we're then able to access
backupExists.

TODO: This demo shows:
	* Start with a version of GetFile that's outdated (backupExists not exposed)
	* Connect to GitHub file server to get latest version
	* Unpause client and watch as backupExists call succeeds.

