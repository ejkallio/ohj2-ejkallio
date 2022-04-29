package fxLP;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import LP.Genre;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

import javafx.stage.Stage;


/**
 * Controllerluokka genrejen käsittelyyn
 * @author Kivikallio
 * @version 29.4.2022
 *
 */
public class GenreController implements ModalControllerInterface<Genre>, Initializable {
    
    @FXML private TextField editGenre;
    
    @FXML private Label labelVirhe;
    
    private Genre genreKohdalla;
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
    
    
    /**
     * Tehdään tarvittavat alustukset
     */
    protected void alusta() {
        if ( editGenre != null )
            editGenre.setOnKeyReleased(e -> kasitteleMuutosGenreen((TextField)(e.getSource())));
    }
    
    
    /**
     * Käsitellään genreen tehty muutos
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosGenreen(TextField edit) {
        if (genreKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = genreKohdalla.setNimi(s);
        if (virhe == null)
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            

    }


    /**
     * Näytetään genren tiedot listaan
     * @param edit kenttä johon näytetään
     * @param genre näytettävä genre
     */
    public static void naytaGenre(TextField edit, Genre genre) {
        edit.setText(genre.getNimi());
    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Luo genren dialogin jossa kysytään genren nimeä
     * @param modalityStage modaalisuus, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @param kentta mihin keskitytään näyttäessä
     * @return null jos painetaan cancel, muuten genreikkuna
     */
    public static Genre kysyNimi(Stage modalityStage, Genre oletus, int kentta) {
        return ModalController.<Genre, GenreController>showModal(
                LPNimiController.class.getResource("GenreDialogView.fxml"),
                "Kirjasto", 
                modalityStage, oletus, 
                ctrl -> ctrl.setKentta(kentta));
    }

}
