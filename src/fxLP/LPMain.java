package fxLP;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Kivikallio
 * @version 3.2.2022
 *
 */
public class LPMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LPGUIView.fxml"));
            final Pane root = ldr.load();
            //final LPGUIController lpCtrl = (LPGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lp.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LP");
            primaryStage.show();
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