package fxLP;

import static fxLP.LPLevyController.getFieldId;

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
import java.util.Collection;
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
import javafx.scene.layout.GridPane;
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
    
    @FXML private ListChooser<Genre> chooserGenret;
    
    @FXML private GridPane gridLevy;
    
    @FXML private TextField editArtisti;

    @FXML private TextField editFormat;

    @FXML private TextField editJulk;

    @FXML private TextField editNimi;

    @FXML private TextField editTietoja;

    @FXML private TextField editVari;

    @FXML private TextField editYhtio;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML private void handleHakuehto() {
        hae(0);
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
        //ModalController.showModal(LPGUIController.class.getResource("LevyDialogView.fxml"), "Levy", null, "");
        muokkaa(1);
    }

    @FXML private void handleNaytaTiedot() {
        ModalController.showModal(LPGUIController.class.getResource("AboutView.fxml"), "LP", null, "");
    }

    @FXML private void handlePoistaGenre() {
        poistaGenre();
    }

    @FXML private void handlePoistaLevy() {
        poistaLevy();
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
    
    
    private String kirjastonnimi = "Elias";
    private Kirjasto kirjasto;
    private Levy levyKohdalla;
    private static Levy apulevy = new Levy();
    //private TextArea areaLevy = new TextArea();
    private TextField edits[];
    private int kentta = 0;
    
    
    /**
     * Tekee tarvittavat muut alustukset
     */
    protected void alusta() {
        //panelLevy.setContent(areaLevy);
        //areaLevy.setFont(new Font("Courier New", 12));
        //panelLevy.setFitToHeight(true);
        
        chooserLevyt.clear();
        chooserLevyt.addSelectionListener(e -> naytaLevy());
        
        cbKentat.clear();
        for (int k = apulevy.ekaKentta(); k < apulevy.getKenttia(); k++)
            cbKentat.add(apulevy.getKysymys(k), null);
        cbKentat.getSelectionModel().select(0);
        
        edits = LPLevyController.luoKentat(gridLevy);
        for (TextField edit: edits)
            if ( edit != null ) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { if (e.getClickCount() > 1) muokkaa(getFieldId(e.getSource(), 0));
                });
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
            }
    }
    
    
    /**
     * Muutosten tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            kirjasto.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
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
    
    
    /**
     * Alustaa kirjaston lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kirjaston tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        kirjastonnimi = nimi;
        setTitle("Kirjasto -" + kirjastonnimi);
        try {
            kirjasto.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Hakee levyjen tiedot listaan
     * @param jnr levyn numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnr) {
        int jnro = jnr;
        if (jnro <= 0) {
            Levy kohdalla = levyKohdalla;
            if ( kohdalla != null ) jnro = kohdalla.getIdNro();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apulevy.ekaKentta();
        
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        chooserLevyt.clear();
        
        int index = 0;
        Collection<Levy> levyt;
        try {
            levyt = kirjasto.etsi(ehto, k);
            int i = 0;
            for (Levy levy:levyt) {
                if (levy.getIdNro() == jnro) index = i;
                chooserLevyt.add(levy.getNimi(), levy);
                i++;
            }
        }catch (SailoException ex) {
            Dialogs.showMessageDialog("Levyn hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserLevyt.setSelectedIndex(index); // muutosviesti joka näyttää levyn
    }
    
    
    /**
     * Luo uuden levyn jota aletaan editoimaan
     */
    protected void lisaaLevy() {
        try {
            Levy uusi = new Levy();
            uusi = LPLevyController.kysyLevy(null, uusi, 1);
            if (uusi == null) return;
            uusi.rekisteroi();
            kirjasto.lisaa(uusi);
            hae(uusi.getIdNro());
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
        }     
    }
    
    
    
    /**
     * Tekee uuden tyhjän genren editointia varten
     */
    public void uusiGenre() {
        if ( levyKohdalla == null ) return;
        Genre gen = new Genre();
        
        gen.rekisteroi();
        gen.defaultGenre(levyKohdalla.getIdNro());
        gen = GenreController.kysyNimi(null, gen, 0);
        try {
            kirjasto.lisaa(gen);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        }
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


        LPLevyController.naytaLevy(edits, levyKohdalla);
        naytaGenret(levyKohdalla);
    }
    
    
    private void naytaGenret(Levy levy) {
        chooserGenret.clear();
        if (levy == null) return;
        
        try {
            List<Genre> genret = kirjasto.annaGenret(levy);
            if (genret.size() == 0) return;
            for (Genre gen: genret)
                naytaGenre(gen);
            
        } catch (SailoException e) {
            naytaVirhe(e.getMessage());
        }
    }
    
    
    private void naytaGenre(Genre gen) {
        // String[] rivi = gen.toString().split("\\|");
        chooserGenret.add(gen.getNimi(), gen);
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
        try {
            List<Genre> genret = kirjasto.annaGenret(levy);
            for (Genre gen:genret)
                gen.tulosta(os);
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Genrejen hakemisessa ongelmia! " + ex.getMessage());
        }

    }
    
    
    private void muokkaa(int k) {
        if (levyKohdalla == null) return;
        try {
            Levy levy;
            levy = LPLevyController.kysyLevy(null, levyKohdalla.clone(), k);
            if (levy == null) return;
            kirjasto.korvaaTaiLisaa(levy);
            hae(levy.getIdNro());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    private void poistaLevy() {
        Levy levy = levyKohdalla;
        if (levy == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko levy: " + levy.getNimi() + "?", "Kyllä", "Ei") )
            return;
        kirjasto.poista(levy);
        int index = chooserLevyt.getSelectedIndex();
        hae(0);
        chooserLevyt.setSelectedIndex(index);
    }
    
    
    private void poistaGenre() {
        int index = chooserGenret.getSelectedIndex();
        if (index < 0) return;
        Genre genre = chooserGenret.getSelectedObject();
        if (genre == null) return;
        kirjasto.poistaGenre(genre);
        naytaGenret(levyKohdalla);
        int genreja = chooserGenret.getItems().size();
        if (index >= genreja) index = genreja -1;
        chooserGenret.getFocusModel().focus(index);
        chooserGenret.getSelectionModel().select(index);
    }
    
    
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki jäsenet");
            Collection<Levy> levyt = kirjasto.etsi("", -1);
            for (Levy levy:levyt) {
                tulosta(os, levy);
                os.println("\n\n");
            }
        }catch (SailoException ex) {
            Dialogs.showMessageDialog("Levyn hakemisessa ongelmia! " + ex.getMessage());
        }
    }

}
