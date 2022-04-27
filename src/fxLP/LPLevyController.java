package fxLP;

import java.net.URL;
import java.util.ResourceBundle;

import LP.Levy;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LPLevyController implements ModalControllerInterface<Levy>, Initializable{
    

    
    
    @FXML private Label labelVirhe;
    
    @FXML private GridPane gridLevy;

    @FXML private ScrollPane panelLevy;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    

    @FXML void handleCancel() {
        levyKohdalla = null;
        ModalController.closeStage(gridLevy);
    }

    @FXML void handleOK() {
        if (levyKohdalla != null && levyKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(gridLevy);
    }

    @FXML void handleValitseKuva(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä lisätä kansikuvia");
    }
    
    
    private Levy levyKohdalla;
    private static Levy apulevy = new Levy();
    private TextField[] edits;
    private int kentta = 0;
    
    
    public static TextField[] luoKentat(GridPane gridLevy) {
        gridLevy.getChildren().clear();
        TextField[] edits = new TextField[apulevy.getKenttia()];
        
        for (int i=0, k = apulevy.ekaKentta(); k < apulevy.getKenttia(); k++, i++) {
            Label label = new Label(apulevy.getKysymys(k));
            gridLevy.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridLevy.add(edit, 1, i);
        }
        return edits;
    }
    
    
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if (edit != null) edit.setText("");
    }
    
    
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !(obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    
    protected void alusta() {
        edits = luoKentat(gridLevy);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased(e -> kasitteleMuutosLevyyn((TextField)(e.getSource())));
        panelLevy.setFitToHeight(true);
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
    
    
    private void kasitteleMuutosLevyyn(int k, TextField edit) {
        if (levyKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = levyKohdalla.setNimi(s); break;
            case 2 : virhe = levyKohdalla.setArtisti(s); break;
            case 3 : virhe = levyKohdalla.setJulkaisu(s); break;
            case 4 : virhe = levyKohdalla.setYhtio(s); break;
            case 5 : virhe = levyKohdalla.setFormat(s); break;
            case 6 : virhe = levyKohdalla.setVari(s); break;
            case 7 : virhe = levyKohdalla.setTietoja(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
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

    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    @Override
    public void handleShown() {
        kentta = Math.max(apulevy.ekaKentta(), Math.min(kentta, apulevy.getKenttia()-1));
        edits[kentta].requestFocus();
    }
    
    
    
    public static void naytaLevy(TextField[] edits, Levy levy) {
        if (levy == null) return;
        for (int k = levy.ekaKentta(); k < levy.getKenttia(); k++) {
            edits[k].setText(levy.anna(k));
        }
    }
    
    
    public static Levy kysyLevy(Stage modalityStage, Levy oletus, int kentta) {
        return ModalController.<Levy, LPLevyController>showModal(
                LPLevyController.class.getResource("LevyDialogView.fxml"),
                "Kirjasto",
                modalityStage, oletus, 
                ctrl -> ctrl.setKentta(kentta)
                );
    }
    
    
    protected void kasitteleMuutosLevyyn(TextField edit) {
        if (levyKohdalla == null) return;
            int k = getFieldId(edit,apulevy.ekaKentta());
            String s = edit.getText();
            String virhe = null;
            virhe = levyKohdalla.aseta(k,s);
            if (virhe == null)
                Dialogs.setToolTipText(edit, "");
                edit.getStyleClass().removeAll("virhe");
    }
}
