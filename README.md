# Asset Saver - Burp Suite

Asset Saver is a Burp Suite extension that enables saving previously loaded
assets from within Burp Suite. It adds a context menu item to HTTP History items and 
the HTTP Request/Response Inspector within multiple tools. Asset Saver extracts the 
raw bytes from the response body into the save file, mitigating encoding issues that 
arise when copying or saving binary data from Burp Suite.

## Build and Installation

### Releases Page

For those who prefer direct downloads, the latest built versions of the
extension can be found on the
[Releases page](https://github.com/gaberust/burp_asset_saver/releases).

### Building with Maven

If you'd like to build the project from source using Maven:

1. Clone the repository:
```bash
git clone https://github.com/gaberust/burp_asset_saver.git
```

2. Navigate to the project directory:
```bash
cd burp_asset_saver
```

3. Build the project using Maven:
```bash
mvn package
```

After these steps, you'll find the built `.jar` file in the `target` directory.
Use the one that does NOT start with `original-`: it does not include the required dependencies.

### Building with IntelliJ

If you're using IntelliJ IDEA:

1. Open IntelliJ and go to `File` -> `Open`.
2. Navigate to the cloned directory and open the project.
3. In the right-hand pane, under the `Maven` tab, expand the `Lifecycle` section.
4. Double-click on `package` to build the project.

After these steps, you'll find the built `.jar` file in the `target`
directory. Use the one that does NOT start with `original-` (it does not
include the required dependencies).

## Feedback and Contributions

Your feedback is important for improving the Asset Saver extension. If you come across any issues or have suggestions, please raise them in the [Issues section](https://github.com/gaberust/burp_asset_saver/issues) of this repository.

Contributions to the codebase are welcome! To contribute, please fork the repository, make your changes, and then submit a pull request.