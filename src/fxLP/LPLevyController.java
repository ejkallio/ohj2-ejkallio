package fxLP;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class LPLevyController implements ModalControllerInterface<String>{
    
    
    @FXML private Label labelVirhe;

    @FXML void handleCancel(ActionEvent event) {
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handleOK(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä muuttaa/lisätä levyjä");
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handleValitseKuva(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä lisätä kansikuvia");
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }

}
