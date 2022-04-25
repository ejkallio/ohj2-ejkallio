package LP;

import java.io.*;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * Genre joka osaa itse huolehtia tunnusnumerostaan
 * 
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Genre {
    private int idNro;
    private int levyNro;
    private String genre;
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan genre
     */
    public Genre() {
        // kesken
    }
    
    
    /**
     * Alustetaan genre halutulle levylle
     * @param levyNro levyn viitenumero
     */
    public Genre(int levyNro) {
        this.levyNro = levyNro;
    }
    
    
    /**
     * Apumetodi, jolla saadaan testiarvo genrelle
     * @param nro viite levyyn
     */
    public void defaultGenre(int nro) {
        levyNro = nro;
        genre = "Alternative rock";                   
    }
    
    
    /**
     * tulostetaan genren tiedot
     * @param out tietovirta
     */
    public void tulosta(PrintStream out) {
        out.println(genre);
    }
    
    
    /**
     * Tulostetaan levyn tiedot
     * @param os tietovirta
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa genrelle seuraavan tunnusnumeron
     * @return genren uusi idNro
     * @example
     * <pre name="test">
     * Genre gen1 = new Genre();
     * gen1.getIdNro() === 0;
     * gen1.rekisteroi();
     * Genre gen2 = new Genre();
     * gen2.rekisteroi();
     * int n1 = gen1.getIdNro();
     * int n2 = gen2.getIdNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        idNro = seuraavaNro;
        seuraavaNro++;
        return idNro;
    }
    
    
    /**
     * palautetaan genren oma tunnusnumero
     * @return tunnusnumero
     */
    public int getIdNro() {
        return idNro;
    }
    
    
    /**
     * palautetaan levyn tunnusnumero
     * @return levyn id
     */
    public int getLevyNro() {
        return levyNro;
    }
    
    
    public String getNimi() {
        return genre;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setIdNro(int nr) {
        idNro = nr;
        if (idNro >= seuraavaNro ) seuraavaNro = idNro + 1;
    }
    

    /**
     * Palauttaa genren tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return genre tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Genre gen = new Genre();
     * gen.parse(" 1 | 2 | Alternative Rock ");
     * gen.toString() === "1|2|Alternative Rock";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getIdNro() + "|" + levyNro + "|" + genre;
    }
    
    
    /**
     * Selvittää genren tiedot | - merkillä erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva idnro.
     * @param rivi josta genren tiedot otetaan
     * @example
     * <pre name="test">
     * Genre gen = new Genre();
     * gen.parse(" 1 | 2 | Alternative Rock ");
     * gen.getIdNro() === 1;
     * gen.toString() === "1|2|Alternative Rock";
     * 
     * gen.rekisteroi();
     * int n = gen.getIdNro();
     * gen.parse("" + (n+20));
     * gen.rekisteroi();
     * gen.getIdNro() === n+20+1;
     * gen.toString() === "" + (n+20+1) + "|2|Alternative Rock";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setIdNro(Mjonot.erota(sb, '|', getIdNro()));
        levyNro = Mjonot.erota(sb, '|', levyNro);
        genre = Mjonot.erota(sb, '|', genre);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return this.toString().equals(obj.toString());
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
        Genre gen = new Genre();
        gen.defaultGenre(2);
        gen.tulosta(System.out);
    }
}
