package run.rekt.burpassetsaver;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implements the context menu item and its behavior for Asset Saver.
 *
 * @author Gabe Rust
 */
public class AssetSaverMenuItemsProvider implements ContextMenuItemsProvider {

    private final MontoyaApi api;

    /**
     * Constructs the MenuItemsProvider implementation with provided Montoya API instance.
     *
     * @param api The MontoyaApi instance that provides access to various Burp functionalities.
     */
    public AssetSaverMenuItemsProvider(MontoyaApi api) {
        this.api = api;
    }

    /**
     * Called by Burp when context menu is created.
     * Creates the context menu items and defines their behavior.
     *
     * @param event Event object containing request/response information.
     * @return List of JMenuItems to add to the request/response context menu.
     */
    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        HttpRequestResponse requestResponse = getSingleRequestResponse(event);
        if (requestResponse == null) {
            return null;
        }

        JMenuItem saveAssetItem = new JMenuItem("Save Asset");
        saveAssetItem.addActionListener((e) -> {
            String filename = getFilename(requestResponse.request());

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setAcceptAllFileFilterUsed(true);
            if (filename != null) {
                fileChooser.setSelectedFile(new File(filename));
            }
            if (fileChooser.showSaveDialog(api.userInterface().swingUtils().suiteFrame()) == JFileChooser.APPROVE_OPTION) {
                try {
                    writeFile(requestResponse.response(), fileChooser.getSelectedFile());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            api.userInterface().swingUtils().suiteFrame(),
                            ex.getMessage() + "\n\nDo you have permission to write there?",
                            "WRITE ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return List.of(saveAssetItem);
    }


    /**
     * Writes the raw bytes of the response body to the file.
     *
     * @param response Response object of context menu.
     * @param file File user has selected to save to.
     * @throws Exception UI portion of code uses this to present error message to user.
     */
    private static void writeFile(HttpResponse response, File file) throws Exception {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(response.body().getBytes());
        }
    }

    /**
     * Extracts filename from URL to pre-fill file chooser text field.
     *
     * @param request Request object containing URL information.
     * @return The extracted filename, or null if filename is blank
     */
    private static String getFilename(HttpRequest request) {
        try {
            String filename = Paths.get(new URI(request.url()).getPath()).getFileName().toString().trim();
            if (filename.isBlank()) {
                return null;
            }
            return filename;
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Extracts the HttpRequestResponse object from the ContextMenuEvent,
     * ensuring only one item is selected and that it has a response.
     *
     * @param event Event object containing request/response information.
     * @return A single HttpRequestResponse object, or null if no response or multiple selected.
     */
    private static HttpRequestResponse getSingleRequestResponse(ContextMenuEvent event) {
        if (event.messageEditorRequestResponse().isPresent()) {
            if (event.messageEditorRequestResponse().get().requestResponse().hasResponse()) {
                return event.messageEditorRequestResponse().get().requestResponse();
            }
        } else if (event.selectedRequestResponses().size() == 1) {
            if (event.selectedRequestResponses().get(0).hasResponse()) {
                return event.selectedRequestResponses().get(0);
            }
        }
        return null;
    }

}
