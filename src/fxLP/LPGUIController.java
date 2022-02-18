package fxLP;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;


/**
 * @author Kivikallio
 * @version 3.2.2022
 *
 */
public class LPGUIController {

    @FXML private ComboBoxChooser<?> cbKentat;

    @FXML private TextField hakuehto;
    
    @FXML private Label labelVirhe;
    
    private String kirjastonnimi = "Elias";
    
    

    @FXML private void handleHakuehto() {
        String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText();
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
    }
    
    
    @FXML private void handleAvaa() {
        avaa();
    }
    

    @FXML private void handleLisaaGenre() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä genrejä");
    }

    @FXML private void handleLisaaLevy() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä levyjä");
    }

    @FXML private void handleMuokkaa() {
        ModalController.showModal(LPGUIController.class.getResource("LevyDialogView.fxml"), "Levy", null, "");
    }

    @FXML private void handleNaytaTiedot() {
        ModalController.showModal(LPGUIController.class.getResource("AboutView.fxml"), "LP", null, "");
    }

    @FXML private void handlePoistaGenre() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa genrejä");
    }

    @FXML private void handlePoistaLevy() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa levyjä");
    }

    @FXML private void handleSiirryKirjastoon() {
        ModalController.showModal(LPGUIController.class.getResource("LPGUIView.fxml"), "Kirjasto", null, "");
    }

    @FXML private void handleSiirryToivelistaan() {
        Dialogs.showMessageDialog("Toivelista kesken");
    }

    @FXML private void handleSulje() {
        tallenna();
        Platform.exit();
    }

    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Muutosten tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä");
    }
    
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    public boolean avaa() {
        String uusinimi = LPNimiController.kysyNimi(null, kirjastonnimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    protected void lueTiedosto(String nimi) {
        kirjastonnimi = nimi;
        setTitle("Kirjasto -" + kirjastonnimi);
        String virhe = "Ei osata lukea vielä";
        // if (virhe != null)
            Dialogs.showMessageDialog(virhe);
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }

}
