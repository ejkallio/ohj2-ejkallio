package LP;

import java.io.*;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Kirjaston levy joka osaa mm. huolehtia omasta tunnusnumerostaan.
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Levy implements Cloneable{
    private int        idNro;
    private String     levynNimi       = "";
    private String     artisti         = "";
    private String     julkaisuPaiva   = "";
    private String     levyYhtio       = "";
    private String     formaatti       = "";
    private String     levynVari       = "";
    private String     lisatietoja     = "";
    
    private static int seuraavaNro     = 1;
    
    
    /**
     * Palauttaa kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 8;
    }
    
    
    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * 
     * Levyjen vertailija
     * 
     */
    public static class Vertailija implements Comparator<Levy> {
        private int k;
        
        @SuppressWarnings("javadoc")
        public Vertailija(int k) {
            this.k = k;
        }
        
        
        @Override
        public int compare(Levy levy1, Levy levy2) {
            return levy1.getAvain(k).compareToIgnoreCase(levy2.getAvain(k));
        }
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monennenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch (k) {
        case 0: return "" + idNro;
        case 1: return "" + levynNimi.toUpperCase();
        case 2: return "" + artisti;
        case 3: return "" + julkaisuPaiva;
        case 4: return "" + levyYhtio;
        case 5: return "" + formaatti;
        case 6: return "" + levynVari;
        case 7: return "" + lisatietoja;
        default: return "null";
        }
    }
    
    
    /**
     * Alustetaan jäsenen merkkijjono-attribuutti tyhjiksi jonoiksi
     * ja tunnusnro = 0;
     */
    public Levy() {
        // Tyhjä toistaiseksi
    }
    
    
    /**
     * @return Levyn nimi
     * @example
     * <pre name="test">
     * Levy levy = new Levy();
     * levy.defaultLevy();
     * levy.getNimi() === "In Rainbows";
     * </pre>
     */
    public String getNimi() {
        return levynNimi;
    }
    
    
    public String getArtisti() {
        return artisti;
    }
    
    
    public String getJulkaisu() {
        return julkaisuPaiva;
    }
    
    
    public String getYhtio() {
        return levyYhtio;
    }
    
    
    public String getFormat() {
        return formaatti;
    }
    
    
    public String getVari() {
        return levynVari;
    }
    
    
    public String getTietoja() {
        return lisatietoja;
    }
    
    
    public String setNimi(String s) {
        levynNimi = s;
        return null;
    }
    
    
    public String setArtisti(String s) {
        artisti = s;
        return null;
    }
    
    
    public String setJulkaisu(String s) {
        julkaisuPaiva = s;
        return null;
    }
    
    
    public String setYhtio(String s) {
        levyYhtio = s;
        return null;
    }
    
    
    public String setFormat(String s) {
        formaatti = s;
        return null;
    }
    
    
    public String setVari(String s) {
        levynVari = s;
        return null;
    }
    
    
    public String setTietoja(String s) {
        lisatietoja = s;
        return null;
    }
    
    
    public String anna(int k) {
        switch (k) {
        case 0: return "" + idNro;
        case 1: return "" + levynNimi;
        case 2: return "" + artisti;
        case 3: return "" + julkaisuPaiva;
        case 4: return "" + levyYhtio;
        case 5: return "" + formaatti;
        case 6: return "" + levynVari;
        case 7: return "" + lisatietoja;
        default: return "null";
        }
    }
    
    
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (k) {
        case 0:
            setIdNro(Mjonot.erota(sb, '§', getIdNro()));
            return null;
        case 1:
            levynNimi = tjono;
            return null;
        case 2: 
            artisti = tjono;
            return null;
        case 3: 
            julkaisuPaiva = tjono;
            return null;
        case 4: 
            levyYhtio = tjono;
            return null;
        case 5: 
            formaatti = tjono;
            return null;
        case 6: 
            levynVari = tjono;
            return null;
        case 7: 
            lisatietoja = tjono;
            return null;
        default:
            return "null";
        }
    }
    
    
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Id nro";
        case 1: return "Levyn nimi";
        case 2: return "Artisti";
        case 3: return "Julkaisupäivä";
        case 4: return "Levy-yhtiö";
        case 5: return "Formaatti";
        case 6: return "Levyn väri";
        case 7: return "Lisätietoja";
        default: return "null";
        }
    }
    
    
    /**
     * annetaan testiarvo levylle
     */
    public void defaultLevy() {
        levynNimi = "In Rainbows";
        artisti = "Radiohead";
        julkaisuPaiva = "10.8.2007";
        formaatti = "LP";
        levyYhtio = "XL";
        levynVari = "Musta";
        
        
    }
    
    
    /**
     * Tulostetaan levyn tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("  " + idNro + "  " + levynNimi + ", " + artisti);
        out.println("  " + julkaisuPaiva + "  " + levyYhtio
                  + "  " + formaatti + "  " + levynVari
                  + "  " + lisatietoja);
    }
    
    
    /**
     * Tulostetaan levyn tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Annetaan levylle seuraava id
     * @return levyn id - numero
     * @example
     * <pre name="test">
     * Levy levy1 = new Levy();
     * levy1.getIdNro() === 0;
     * levy1.rekisteroi();
     * Levy levy2 = new Levy();
     * levy2.rekisteroi();
     * int n1 = levy1.getIdNro();
     * int n2 = levy2.getIdNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        idNro = seuraavaNro;
        seuraavaNro++;
        return idNro;
    }
    
    
    /**
     * 
     * @return levyn id
     */
    public int getIdNro() {
        return idNro;
    }
    
    
    /**
     * Asettaa idnumeron ja samalla varmistaa että 
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava idnro
     */
    private void setIdNro(int nr) {
        idNro = nr;
        if (idNro >= seuraavaNro) seuraavaNro = idNro + 1;
    }
    
    
    /**
     * Palauttaa levyn tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return levy tolppaeroteltuna merkkijonona.
     * @example
     * <pre name="test">
     * Levy levy = new Levy();
     * levy.parse(" 1 | In Rainbows | Radiohead");
     * levy.toString().startsWith("1|In Rainbows|Radiohead|") === true;
     * </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
    }
    
    
    /**
     * Selvittää levyn tiedot merkillä | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva idNro.
     * @param rivi josta levyn tiedot otetaan
     * @example
     * <pre name="test">
     * Levy levy = new Levy();
     * levy.parse(" 1 | In Rainbows | Radiohead");
     * levy.toString().startsWith("1|In Rainbows|Radiohead|") === true;     
     * 
     * levy.rekisteroi();
     * int n = levy.getIdNro();
     * levy.parse(""+(n+20));
     * levy.rekisteroi();
     * levy.getIdNro() === n+20+1;
     * * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    
    @Override
    public Levy clone() throws CloneNotSupportedException {
        Levy uusi;
        uusi = (Levy) super.clone();
        return uusi;
    }
    
    
    public boolean equals(Levy levy) {
        if (levy == null) return false;
        for (int k = 0; k < getKenttia(); k++)
            if (!anna(k).equals(levy.anna(k)) ) return false;
        return true;
    }
    
    
    @Override
    public boolean equals(Object levy) {
        if (levy instanceof Levy) return equals((Levy)levy);
        return false;
    }
    
    
    @Override
    public int hashCode() {
        return idNro;
    }
    
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Levy radiohead = new Levy();
        Levy radiohead2 = new Levy();
        radiohead.rekisteroi();
        radiohead2.rekisteroi();
        radiohead.tulosta(System.out);
        radiohead.defaultLevy();
        radiohead.tulosta(System.out);
        
        radiohead2.defaultLevy();
        radiohead2.tulosta(System.out);
        
        radiohead2.defaultLevy();
        radiohead2.tulosta(System.out);        
    }
    
    
}
