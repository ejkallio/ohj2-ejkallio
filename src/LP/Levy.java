package LP;

import java.io.*;


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
