package fxLP;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import LP.Kirjasto;
import fi.jyu.mit.fxgui.ListChooser;

/**
 * @author Kivikallio
 * @version 3.2.2022
 *
 */
public class LPMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("LPGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final LPGUIController lpCtrl = (LPGUIController) ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lp.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LP");
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !lpCtrl.voikoSulkea() ) event.consume();
                });
            
            Kirjasto kirjasto = new Kirjasto();
            lpCtrl.setKirjasto(kirjasto);
                    
            primaryStage.show();           
            
            Application.Parameters params = getParameters();
            if (params.getRaw().size() > 0)
                lpCtrl.lueTiedosto(params.getRaw().get(0));
            else
                if (!lpCtrl.avaa()) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {               
        launch(args);
    }
}