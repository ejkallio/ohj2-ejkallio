package LP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Levykirjaston genret, joka osaa mm. lisätä uuden genren
 * 
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Genret implements Iterable<Genre> {
    
    // private String tiedostonNimi = "";
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";
    
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
        muutettu = true;
    }
    
    
    /**
     * Lukee levykirjaston tiedostosta. 
     * @param tied tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     *  Genret genret = new Genret();
     *  Genre gen1 = new Genre(); gen1.defaultGenre(1);
     *  Genre gen2 = new Genre(); gen2.defaultGenre(2);
     *  Genre gen3 = new Genre(); gen3.defaultGenre(3);
     *  Genre gen4 = new Genre(); gen4.defaultGenre(4);
     *  Genre gen5 = new Genre(); gen5.defaultGenre(5);
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  genret.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  genret.lisaa(gen1);
     *  genret.lisaa(gen2);
     *  genret.lisaa(gen3);
     *  genret.lisaa(gen4);
     *  genret.lisaa(gen5);
     *  genret.tallenna();
     *  genret = new Genret();
     *  genret.lueTiedostosta(tiedNimi);
     *  Iterator<Genre> i = genret.iterator();
     *  i.next().toString() === gen1.toString();
     *  i.next().toString() === gen2.toString();
     *  i.next().toString() === gen3.toString();
     *  i.next().toString() === gen4.toString();
     *  i.next().toString() === gen5.toString();
     *  i.hasNext() === false;
     *  genret.lisaa(gen5);
     *  genret.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Genre gen = new Genre();
                gen.parse(rivi);
                lisaa(gen);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annettu tiedosto
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa levykirjaston tiedostoon. 
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if ... System.err.println("Ei voi nimetä");
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Genre gen : this) {
                fo.println(gen.toString());
            }
        } catch (FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName() + "kirjoittamisessa ongelmia");
        }
            
            
        muutettu = false;
    }
    
    
    /**
     * Poistaa halutun genren
     * @param genre joka halutaan poistaa
     * @return true jos poistettava löytyi
     */
    public boolean poista(Genre genre) {
        boolean ret = alkiot.remove(genre);
        if (ret) muutettu = true;
        return ret;
    }
    
    
    public int poistaLevynGenret(int idNro) {
        int n = 0;
        for (Iterator<Genre> it = alkiot.iterator(); it.hasNext();) {
            Genre gen = it.next();
            if ( gen.getLevyNro() == idNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
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
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
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
