package fxLP;

import java.net.URL;
import java.util.ResourceBundle;

import LP.Levy;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LPLevyController implements ModalControllerInterface<Levy>, Initializable{
    

    @FXML private TextField editArtisti;

    @FXML private TextField editFormat;

    @FXML private TextField editJulk;

    @FXML private TextField editNimi;

    @FXML private TextField editTietoja;

    @FXML private TextField editVari;

    @FXML private TextField editYhtio;
    
    @FXML private Label labelVirhe;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    

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
    
    
    private Levy levyKohdalla;
    private TextField edits[];
    
    
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }
    
    
    protected void alusta() {
        edits = new TextField[] {editNimi, editArtisti, editJulk, editFormat, editYhtio, editVari, editTietoja};
    }


    @Override
    public void setDefault(Levy oletus) {
        levyKohdalla = oletus;
        naytaLevy(edits, levyKohdalla);
    }
    
    
    @Override
    public Levy getResult() {
        return levyKohdalla;
    }

    
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }
    
    
    public static void naytaLevy(TextField[] edits, Levy levy) {
        if (levy == null) return;
        edits[0].setText(levy.getNimi());
        edits[1].setText(levy.getArtisti());
        edits[2].setText(levy.getJulkaisu());
        edits[3].setText(levy.getFormat());
        edits[4].setText(levy.getYhtio());
        edits[5].setText(levy.getVari());
        edits[6].setText(levy.getTietoja());
    }
    
    
    public static Levy kysyLevy(Stage modalityStage, Levy oletus) {
        return ModalController.<Levy, LPLevyController>showModal(
                LPLevyController.class.getResource("LevyDialogView.fxml"),
                "Kirjasto",
                modalityStage, oletus, null
                );
    }
}
