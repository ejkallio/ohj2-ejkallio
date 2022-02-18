package fxLP;


import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LPNimiController implements ModalControllerInterface<String> {
    
    
    @FXML private TextField textVastaus;
    
    private String vastaus = null;

    
    @FXML void handleCancel() {
        ModalController.closeStage(textVastaus);
    }

    
    @FXML void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
    
    
    @Override
    public String getResult() {
        return vastaus;
    }
    
    
    @Override 
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
    }

    
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
    
    
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(LPNimiController.class.getResource("LPNimiView.fxml"), oletus, modalityStage, "");
    }
    
    
}
