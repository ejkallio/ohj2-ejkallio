package LP;

import java.io.*;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * Kirjaston levy joka osaa mm. huolehtia omasta tunnusnumerostaan.
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Levy {
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
        return "" + 
                getIdNro() + "|" +
                levynNimi + "|" + 
                artisti + "|" + 
                julkaisuPaiva + "|" +
                levyYhtio + "|" +
                formaatti + "|" + 
                levynVari + "|" + 
                lisatietoja;
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
        setIdNro(Mjonot.erota(sb, '|', getIdNro()));
        levynNimi = Mjonot.erota(sb, '|', levynNimi);
        artisti = Mjonot.erota(sb, '|', artisti);
        julkaisuPaiva = Mjonot.erota(sb, '|', julkaisuPaiva);
        levyYhtio = Mjonot.erota(sb, '|', levyYhtio);
        formaatti = Mjonot.erota(sb, '|', formaatti);
        levynVari = Mjonot.erota(sb, '|', levynVari);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }
    
    
    @Override
    public boolean equals(Object levy) {
        if (levy == null) return false;
        return this.toString().equals(levy.toString());
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
