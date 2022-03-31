package fxLP;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import LP.Genre;
import LP.Kirjasto;
import LP.Levy;
import LP.SailoException;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


/**
 * @author Kivikallio
 * @version 3.2.2022
 *
 */
public class LPGUIController implements Initializable {

    @FXML private ComboBoxChooser<?> cbKentat;

    @FXML private TextField hakuehto;
    
    @FXML private Label labelVirhe;
    
    @FXML private ScrollPane panelLevy;
    
    @FXML private ListChooser<Levy> chooserLevyt;
    
    private String kirjastonnimi = "Elias";
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
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
        // ModalController.showModal(LPGUIController.class.getResource("GenreDialogView.fxml"), "Genre", null, "");
        uusiGenre();
    }

    @FXML private void handleLisaaLevy() {
        lisaaLevy();
        
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
    
    @FXML void handleApua(ActionEvent event) {
        avustus();
    }
    
    
    
    private Kirjasto kirjasto;
    private Levy levyKohdalla;
    private TextArea areaLevy = new TextArea();
    
    
    /**
     * Tekee tarvittavat muut alustukset
     */
    protected void alusta() {
        panelLevy.setContent(areaLevy);
        areaLevy.setFont(new Font("Courier New", 12));
        panelLevy.setFitToHeight(true);
        
        chooserLevyt.clear();
        chooserLevyt.addSelectionListener(e -> naytaLevy());
    }
    
    
    /**
     * Muutosten tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä");
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
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
    
    
    /**
     * Hakee levyjen tiedot listaan
     * @param jnro levyn numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        chooserLevyt.clear();
        
        int index = 0;
        for(int i = 0; i < kirjasto.getLevyja(); i++) {
            Levy levy = kirjasto.annaLevy(i);
            if(levy.getIdNro() == jnro) index = i;
            chooserLevyt.add(levy.getNimi(), levy);
        }
        chooserLevyt.setSelectedIndex(index); // muutosviesti joka näyttää levyn
    }
    
    
    /**
     * Luo uuden levyn jota aletaan editoimaan
     */
    protected void lisaaLevy() {
        Levy uusi = new Levy();
        uusi.rekisteroi();
        uusi.defaultLevy();
        try {
            kirjasto.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getIdNro());
        
        
    }
    
    
    /**
     * Tekee uuden tyhjän genren editointia varten
     */
    public void uusiGenre() {
        if ( levyKohdalla == null ) return;
        Genre gen = new Genre();
        gen.rekisteroi();
        gen.defaultGenre(levyKohdalla.getIdNro());
        kirjasto.lisaa(gen);
        hae(levyKohdalla.getIdNro());
    }
    
    
    /**
     * @param kirjasto Kirjasto jota käytetään tässä käyttöliittymässä
     */
    public void setKirjasto(Kirjasto kirjasto) {
        this.kirjasto = kirjasto;
        naytaLevy();
    }
    
    
    /**
     * Näyttää listasta valitun levyn tiedot
     */
    protected void naytaLevy() {
        levyKohdalla = chooserLevyt.getSelectedObject();
        
        if (levyKohdalla == null) return;
        
        areaLevy.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaLevy)) {
            tulosta(os, levyKohdalla);
        }
    }
    
    
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2022k/ht/ejkallio");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    public void tulosta(PrintStream os, final Levy levy) {
        os.println("--------------------------------------------");
        levy.tulosta(os);
        os.println("--------------------------------------------");
        List<Genre> genret = kirjasto.annaGenret(levy);
        for (Genre gen:genret)
            gen.tulosta(os);
    }

}
