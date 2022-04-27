package LP;

import java.io.File;
import java.util.*;


/**
 * Kirjasto-luokka, joka huolehtii levyistä. Pääosin kaikki
 * metodit ovat "välittäjämetodeja" levyihin.
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Kirjasto {
    private Levyt levyt = new Levyt();
    private Genret genret = new Genret();
    
    /**
     * 
     * @return kirjaston levymäärä
     */
    public int getLevyja() {
        return levyt.getLkm();
    }
    
    
    /**
     * Poistaa kirjastosta ne joilla on nro. kesken
     * @param levy joka halutaan poistaa
     * @return montako levyä poistettiin
     */
    public int poista(Levy levy) {
        if (levy == null) return 0;
        int ret = levyt.poista(levy.getIdNro());
        genret.poistaLevynGenret(levy.getIdNro());
        return ret;
    }
    
    
    public void poistaGenre(Genre genre) {
        genret.poista(genre);
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
     * kirjasto.lisaa(levy1);
     * kirjasto.lisaa(levy2);
     * kirjasto.lisaa(levy1);
     * Collection<Levy> loytyneet = kirjasto.etsi("",-1);
     * Iterator<Levy> it = loytyneet.iterator();
     * it.next() === levy1;
     * it.next() === levy2;
     * it.next() === levy1;
     * </pre>
     */
    public void lisaa(Levy levy) throws SailoException {
        levyt.lisaa(levy);
    }
    
    
    public void korvaaTaiLisaa(Levy levy) throws SailoException {
        levyt.korvaaTaiLisaa(levy);
    }
    
    
    
    /**
     * Lisää uuden genren kirjastoon
     * @param gen lisättävä genre
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Genre gen) throws SailoException {
        genret.lisaa(gen);
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
     * Palauttaa "taulukossa" hakuehtoon vastaavien levyjen viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä jäsenistä
     * @throws SailoException jos jotakin menee väärin
     */
    public Collection<Levy> etsi(String hakuehto, int k) throws SailoException {
        return levyt.etsi(hakuehto, k);
    }
    
    
    /**
     * Haetaan kaikki levyn genret
     * @param levy levy jolle genrejä haetaan
     * @return tietorakenne jossa viitteet löydettyihin genreihin
     * @throws SailoException jos jotakin menee väärin
     * @example
     * <pre name="test">
     * #THROWS SailoException
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
    public List<Genre> annaGenret(Levy levy)  throws SailoException{
        return genret.annaGenret(levy.getIdNro());
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        levyt.setTiedostonPerusNimi(hakemistonNimi + "levyt");
        genret.setTiedostonPerusNimi(hakemistonNimi + "genret");
    }
    
    
    /**
     * Lukee kirjaston tiedot tiedostosta
     * @param nimi nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.*;
     * #import java.util.*;
     * 
     * Kirjasto kirjasto = new Kirjasto();
     * 
     * Levy levy1 = new Levy(); levy1.defaultLevy(); levy1.rekisteroi();
     * Levy levy2 = new Levy(); levy2.defaultLevy(); levy2.rekisteroi();
     * Genre gen1 = new Genre(); gen1.defaultGenre(levy2.getIdNro());
     * Genre gen2 = new Genre(); gen2.defaultGenre(levy1.getIdNro());
     * Genre gen3 = new Genre(); gen3.defaultGenre(levy2.getIdNro());
     * Genre gen4 = new Genre(); gen4.defaultGenre(levy1.getIdNro());
     * Genre gen5 = new Genre(); gen5.defaultGenre(levy2.getIdNro());
     * 
     * String hakemisto = "testikirjasto";
     * File dir = new File(hakemisto);
     * File ftied = new File(hakemisto+"/levyt.dat");
     * File fhtied = new File(hakemisto+"/genret.dat");
     * dir.mkdir();
     * ftied.delete();
     * fhtied.delete();
     * kirjasto.lueTiedostosta(hakemisto); #THROWS SailoException
     * kirjasto.lisaa(levy1);
     * kirjasto.lisaa(levy2);
     * kirjasto.lisaa(gen1);
     * kirjasto.lisaa(gen2);
     * kirjasto.lisaa(gen3);
     * kirjasto.lisaa(gen4);
     * kirjasto.lisaa(gen5);
     * kirjasto.tallenna();
     * kirjasto = new Kirjasto();
     * kirjasto.lueTiedostosta(hakemisto);
     * Collection<Levy> kaikki = kirjasto.etsi("",-1);
     * Iterator<Levy> it = kaikki.iterator();
     * it.next() === levy1;
     * it.next() === levy2;
     * it.hasNext() === false;
     * List<Genre> loytyneet = kirjasto.annaGenret(levy1);
     * Iterator<Genre> ih = loytyneet.iterator();
     * ih.next() === gen2;
     * ih.next() === gen4;
     * ih.hasNext() === false;
     * loytyneet = kirjasto.annaGenret(levy2);
     * ih = loytyneet.iterator();
     * ih.next() === gen1;
     * ih.next() === gen3;
     * ih.next() === gen5;
     * ih.hasNext() === false;
     * kirjasto.lisaa(levy2);
     * kirjasto.lisaa(gen5);
     * kirjasto.tallenna();
     * ftied.delete() === true;
     * fhtied.delete() === true;
     * File fbak = new File(hakemisto+"/levyt.bak");
     * File fhbak = new File(hakemisto+"/genret.bak");
     * fbak.delete() === true;
     * fhbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        levyt = new Levyt();
        genret = new Genret();
        
        setTiedosto(nimi);
        levyt.lueTiedostosta();
        genret.lueTiedostosta();
    }
    
    
    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            levyt.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            genret.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
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

            Collection<Levy> levyt = kirjasto.etsi("", -1);
            int i = 0;
            for (Levy levy : levyt) {
                System.out.println("Jäsen paikassa: " + i);
                levy.tulosta(System.out);
                List<Genre> loytyneet = kirjasto.annaGenret(levy);
                for (Genre genre : loytyneet)
                    genre.tulosta(System.out);
                i++;
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
