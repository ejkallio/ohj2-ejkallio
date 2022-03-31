package LP;

import java.util.*;


/**
 * Levykirjaston genret, joka osaa mm. lisätä uuden genren
 * 
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Genret implements Iterable<Genre> {
    
    private String tiedostonNimi = "";
    
    
    private final Collection<Genre> alkiot = new ArrayList<Genre>();
    
    
    /**
     * Genren alustaminen
     */
    public Genret() {
        // kesken
    }
    
    
    /**
     * Lisää uuden genren tietorakeenteeseen. Ottaa genren omistukseensa.
     * @param gen lisättävä genre. Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Genre gen) {
        alkiot.add(gen);
    }
    
    
    /**
     * Lukee levykirjaston tiedostosta. KESKEN
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa" + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa levykirjaston tiedostoon. KESKEN
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa kirjaston Genrejen lukumäärän
     * @return genrejen lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Iteraattori kaikkien genrejen läpikäymiseen
     * @return genreiteraattori
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Genret genret = new Genret();
     *  Genre gen21 = new Genre(2); genret.lisaa(gen21);
     *  Genre gen11 = new Genre(1); genret.lisaa(gen11);
     *  Genre gen22 = new Genre(2); genret.lisaa(gen22);
     *  Genre gen12 = new Genre(1); genret.lisaa(gen12);
     *  Genre gen23 = new Genre(2); genret.lisaa(gen23);
     * 
     *  Iterator<Genre> i2=genret.iterator();
     *  i2.next() === gen21;
     *  i2.next() === gen11;
     *  i2.next() === gen22;
     *  i2.next() === gen12;
     *  i2.next() === gen23;
     *  i2.next() === gen12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Genre gen:genret ) { 
     *    gen.getLevyNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Genre> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan kaikki jäsen harrastukset
     * @param tunnusnro jäsenen tunnusnumero jolle harrastuksia haetaan
     * @return tietorakenne jossa viitteet löydettyihin genreihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Genret genret = new Genret();
     *  Genre gen21 = new Genre(2); genret.lisaa(gen21);
     *  Genre gen11 = new Genre(1); genret.lisaa(gen11);
     *  Genre gen22 = new Genre(2); genret.lisaa(gen22);
     *  Genre gen12 = new Genre(1); genret.lisaa(gen12);
     *  Genre gen23 = new Genre(2); genret.lisaa(gen23);
     *  Genre gen51 = new Genre(5); genret.lisaa(gen51);
     *  
     *  List<Genre> loytyneet;
     *  loytyneet = genret.annaGenret(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = genret.annaGenret(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == gen11 === true;
     *  loytyneet.get(1) == gen12 === true;
     *  loytyneet = genret.annaGenret(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == gen51 === true;
     * </pre>
     */
    public List<Genre> annaGenret(int tunnusnro) {
        List<Genre> loydetyt = new ArrayList<Genre>();
        for (Genre gen : alkiot)
            if (gen.getLevyNro() == tunnusnro) loydetyt.add(gen);
        return loydetyt;
    }
    
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Genret genret = new Genret();
        Genre gen1 = new Genre();
        gen1.defaultGenre(2);
        Genre gen2 = new Genre();
        gen2.defaultGenre(1);
        Genre gen3 = new Genre();
        gen3.defaultGenre(2);
        Genre gen4 = new Genre();
        gen4.defaultGenre(2);
        
        genret.lisaa(gen1);
        genret.lisaa(gen2);
        genret.lisaa(gen3);
        genret.lisaa(gen2);
        genret.lisaa(gen4);
        
        System.out.println("============ Genret testi ============");
        
        List<Genre> genret2 = genret.annaGenret(2);
        
        for (Genre gen : genret2) {
            System.out.print(gen.getLevyNro() + " ");
            gen.tulosta(System.out);
        }
    }
}
