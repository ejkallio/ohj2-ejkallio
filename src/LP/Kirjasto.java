package LP;

import java.util.*;


/**
 * Kirjasto-luokka, joka huolehtii levyistä. Pääosin kaikki
 * metodit ovat "välittäjämetodeja" levyihin.
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Kirjasto {
    private final Levyt levyt = new Levyt();
    private final Genret genret = new Genret();
    
    /**
     * 
     * @return kirjaston levymäärä
     */
    public int getLevyja() {
        return levyt.getLkm();
    }
    
    
    /**
     * Poistaa kirjastosta ne joilla on nro. kesken
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako levyä poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
    
    
    /**
     * lisää kirjastoon uuden levyn
     * @param levy lisättävä levy
     * @throws SailoException jos lisäystä ei voi tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kirjasto kirjasto = new Kirjasto();
     * Levy levy1 = new Levy(), levy2 = new Levy();
     * levy1.rekisteroi(); levy2.rekisteroi();
     * kirjasto.getLevyja() === 0;
     * kirjasto.lisaa(levy1); kirjasto.getLevyja() === 1;
     * kirjasto.lisaa(levy2); kirjasto.getLevyja() === 2;
     * kirjasto.lisaa(levy1); kirjasto.getLevyja() === 3;
     * kirjasto.getLevyja() === 3;
     * kirjasto.annaLevy(0) === levy1;
     * kirjasto.annaLevy(1) === levy2;
     * kirjasto.annaLevy(2) === levy1;
     * kirjasto.annaLevy(3) === levy1; #THROWS IndexOutOfBoundsException
     * kirjasto.lisaa(levy1); kirjasto.getLevyja() === 4;
     * kirjasto.lisaa(levy1); kirjasto.getLevyja() === 5;
     * kirjasto.lisaa(levy1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Levy levy) throws SailoException {
        levyt.lisaa(levy);
    }
    
    
    /**
     * Palauttaa i:n levyn
     * @param i monesko levy palautetaan
     * @return viite i:teen levyyn
     * @throws IndexOutOfBoundsException jos i väärin 
     */
    public Levy annaLevy(int i) throws IndexOutOfBoundsException {
        return levyt.anna(i);
    }
    
    
    /**
     * Lukee kirjaston tiedot tiedostosta
     * @param nimi nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        levyt.lueTiedostosta(nimi);
        genret.lueTiedostosta(nimi);
    }
    
    
    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        levyt.talleta();
        genret.talleta();
    }
    
    
    /**
     * Lisää uuden genren kirjastoon
     * @param gen lisättävä genre
     */
    public void lisaa(Genre gen) {
        genret.lisaa(gen);
    }
    
    
    /**
     * Haetaan kaikki levyn genret
     * @param levy levy jolle genrejä haetaan
     * @return tietorakenne jossa viitteet löydettyihin genreihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Kirjasto kirjasto = new Kirjasto();
     * Levy levy1 = new Levy(), levy2 = new Levy(), levy3 = new Levy();
     * levy1.rekisteroi(); levy2.rekisteroi(); levy3.rekisteroi();
     * int id1 = levy1.getIdNro();
     * int id2 = levy2.getIdNro();
     * Genre gen1 = new Genre(id1); kirjasto.lisaa(gen1);
     * Genre gen2 = new Genre(id1); kirjasto.lisaa(gen2);
     * Genre gen3 = new Genre(id2); kirjasto.lisaa(gen3);
     * Genre gen4 = new Genre(id2); kirjasto.lisaa(gen4);
     * Genre gen5 = new Genre(id2); kirjasto.lisaa(gen5);
     * 
     * List<Genre> loytyneet;
     * loytyneet = kirjasto.annaGenret(levy3);
     * loytyneet.size() === 0;
     * loytyneet = kirjasto.annaGenret(levy1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == gen1 === true;
     * loytyneet.get(1) == gen2 === true;
     * loytyneet = kirjasto.annaGenret(levy2);
     * loytyneet.size() === 3;
     * loytyneet.get(0) == gen3 === true;
     * </pre>
     */
    public List<Genre> annaGenret(Levy levy) {
        return genret.annaGenret(levy.getIdNro());
    }
    
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kirjasto kirjasto = new Kirjasto();
        
        try {
            
            
            Levy rh1 = new Levy(), rh2 = new Levy();
            rh1.rekisteroi();
            rh1.defaultLevy();
            rh2.rekisteroi();
            rh2.defaultLevy();
            
            kirjasto.lisaa(rh1);
            kirjasto.lisaa(rh2);
            int id1 = rh1.getIdNro();
            int id2 = rh2.getIdNro();
            Genre gen11 = new Genre(id1); gen11.defaultGenre(id1); kirjasto.lisaa(gen11);
            Genre gen12 = new Genre(id1); gen12.defaultGenre(id1); kirjasto.lisaa(gen12);
            Genre gen21 = new Genre(id2); gen21.defaultGenre(id2); kirjasto.lisaa(gen21);
            Genre gen22 = new Genre(id2); gen22.defaultGenre(id2); kirjasto.lisaa(gen22);
            Genre gen23 = new Genre(id2); gen23.defaultGenre(id2); kirjasto.lisaa(gen23);
            
            System.out.println("============ Kirjaston testi ============");

             
            for (int i = 0; i < kirjasto.getLevyja(); i++) {
                Levy levy = kirjasto.annaLevy(i);
                System.out.println("Jäsen paikassa: " + i);
                levy.tulosta(System.out);
                List<Genre> loytyneet = kirjasto.annaGenret(levy);
                for (Genre genre : loytyneet)
                    genre.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
