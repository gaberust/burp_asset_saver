package run.rekt.burpassetsaver;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

/**
 * The main extension class for the "Asset Saver" Burp Suite extension.
 *
 * @author Gabe Rust
 */
@SuppressWarnings("unused")
public class AssetSaverExtension implements BurpExtension {

    /**
     * Initializes the extension with provided API.
     * Sets the extension name and registers the ContextMenuItemsProvider implementation.
     *
     * @param api The MontoyaApi instance that provides access to various burp functionalities.
     */
    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName("Asset Saver");
        api.userInterface().registerContextMenuItemsProvider(new AssetSaverMenuItemsProvider(api));
    }

}
