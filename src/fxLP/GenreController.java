package fxLP;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import LP.Genre;
import LP.Levy;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

import javafx.stage.Stage;


public class GenreController implements ModalControllerInterface<Genre>, Initializable {
    
    @FXML private TextField editGenre;
    
    @FXML private Label labelVirhe;
    
    private Genre genreKohdalla;
    private String vastaus = null;
    private int kentta = 0;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML
    void handleCancel() {
        genreKohdalla = null;
        ModalController.closeStage(editGenre);
    }

    @FXML void handleOK() {
        if (genreKohdalla != null && genreKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(editGenre);
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    @Override
    public Genre getResult() {
        return genreKohdalla;
    }
    
    
    @Override 
    public void setDefault(Genre oletus) {
        genreKohdalla = oletus;
        naytaGenre(editGenre, genreKohdalla);
    }

    
    @Override
    public void handleShown() {
        editGenre.requestFocus();
    }
    
    
    protected void alusta() {
        if ( editGenre != null )
            editGenre.setOnKeyReleased(e -> kasitteleMuutosGenreen((TextField)(e.getSource())));
    }
    
    
    protected void kasitteleMuutosGenreen(TextField edit) {
        if (genreKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = genreKohdalla.setNimi(s);
        if (virhe == null)
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            

    }



    public static void naytaGenre(TextField edit, Genre genre) {
        edit.setText(genre.getNimi());
    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    public static Genre kysyNimi(Stage modalityStage, Genre oletus, int kentta) {
        return ModalController.<Genre, GenreController>showModal(
                LPNimiController.class.getResource("GenreDialogView.fxml"),
                "Kirjasto", 
                modalityStage, oletus, 
                ctrl -> ctrl.setKentta(kentta));
    }

}
