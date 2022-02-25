package fxLP;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

import javafx.stage.Stage;


public class GenreController implements ModalControllerInterface<String> {
    
    @FXML
    private TextField genrenimi;
    
    private String vastaus = null;

    @FXML
    void handleCancel(ActionEvent event) {
        ModalController.closeStage(genrenimi);
    }

    @FXML void handleOK() {
        vastaus = genrenimi.getText();
        ModalController.closeStage(genrenimi);
    }
    
    
    @Override
    public String getResult() {
        return vastaus;
    }
    
    
    @Override 
    public void setDefault(String oletus) {
        genrenimi.setText(oletus);
    }

    
    @Override
    public void handleShown() {
        genrenimi.requestFocus();
    }
    
    
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(LPNimiController.class.getResource("LPNimiView.fxml"), oletus, modalityStage, "");
    }
    
}
