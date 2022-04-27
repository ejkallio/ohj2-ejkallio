package LP;

import java.io.*;
import java.util.*;

import fi.jyu.mit.ohj2.WildChars;


/**
 * Kirjaston levyt, joka osaa mm. lisätä uuden levyn
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Levyt implements Iterable<Levy> {
    public static final int MAX_LEVYJA    = 15;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
    private Levy alkiot[] = new Levy[MAX_LEVYJA];
    
    
    /**
     * Oletusmuodostaja
     */
    public Levyt() {
        //
    }
    
    
    /**
     * Lisää uuden levyn tietorakenteeseen. ottaa levyn omistukseensa.
     * @param levy lisättävän levyn viite
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Levyt levyt = new Levyt();
     * Levy levy1 = new Levy(), levy2 = new Levy();
     * levyt.getLkm() === 0;
     * levyt.lisaa(levy1); levyt.getLkm() === 1;
     * levyt.lisaa(levy2); levyt.getLkm() === 2;
     * levyt.lisaa(levy1); levyt.getLkm() === 3;
     * levyt.anna(0) === levy1;
     * levyt.anna(1) === levy2;
     * levyt.anna(2) === levy1;
     * levyt.anna(1) == levy1 === false;
     * levyt.anna(1) == levy2 === true;
     * levyt.anna(3) === levy1; #THROWS IndexOutOfBoundsException
     * levyt.lisaa(levy1); levyt.getLkm() === 4;
     * levyt.lisaa(levy1); levyt.getLkm() === 5;
     * levyt.lisaa(levy1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Levy levy) throws SailoException {
        if (lkm >= alkiot.length) {
            //throw new SailoException("Liikaa alkioita");
            int koko = alkiot.length + 10;
            Levy alkiot2[] = new Levy[koko];
            for(int i = 0; i < alkiot.length; i++) {
                alkiot2[i] = alkiot[i];
            }
            alkiot = alkiot2;
        }
        alkiot[lkm] = levy;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * palauttaa viitteen i:teen levyyn
     * @param i monennenko levyn viite halutaan
     * @return viite levyyn jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Levy anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    public void korvaaTaiLisaa(Levy levy) throws SailoException {
        int id = levy.getIdNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getIdNro() == id) {
                alkiot[i] = levy;
                muutettu = true;
                return;
            }
        }
        lisaa(levy);
    }
    
    
    /**
     * Lukee kirjaston tiedostosta, kesken
     * @param tied tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Levyt levyt = new Levyt();
     * Levy levy1 = new Levy(), levy2 = new Levy();
     * levy1.defaultLevy();
     * levy2.defaultLevy();
     * String hakemisto = "testielias";
     * String tiedNimi = hakemisto + "/levyt";
     * File ftied = new File(tiedNimi+".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * levyt.lueTiedostosta(tiedNimi); #THROWS SailoException
     * levyt.lisaa(levy1);
     * levyt.lisaa(levy2);
     * levyt.tallenna();
     * levyt = new Levyt();
     * levyt.lueTiedostosta(tiedNimi);
     * Iterator<Levy> i = levyt.iterator();
     * i.next() === levy1;
     * i.next() === levy2;
     * i.hasNext() === false;
     * levyt.lisaa(levy2);
     * levyt.tallenna();
     * ftied.delete() === true;
     * File fbak = new File(tiedNimi+".bak");
     * fbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            kokoNimi = fi.readLine();
            if (kokoNimi == null) throw new SailoException("Kirjaston nimi puuttuu");
            String rivi = fi.readLine();
            if (rivi == null) throw new SailoException("Maksimikoko puuttuu");
            //int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin
            
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Levy levy = new Levy();
                levy.parse(rivi);
                lisaa(levy);
            }
            muutettu = false;
            
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa levyt tiedostoon
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
         if (!muutettu) return;
         
         File fbak = new File(getBakNimi());
         File ftied = new File(getTiedostonNimi());
         fbak.delete(); // if ... System.err.println("Ei voi tuhota")
         ftied.renameTo(fbak); //if ... System.err.println("Ei voi nimetä");
         
         try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
             fo.println(getKokoNimi());
             fo.println(alkiot.length);
             for (Levy levy : this) {
                 fo.println(levy.toString());
             }
             
             
         } catch (FileNotFoundException ex) {
             throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
         } catch (IOException ex) {
             throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
         }
         
         
         muutettu = false;
    }
    
    
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa kirjaston levyjen lukumäärän
     * @return levyjen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    public class LevytIterator implements Iterator<Levy> {
        private int kohdalla = 0;
        
        
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        
        
        @Override
        public Levy next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }
        
        
        @Override 
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    
    @Override
    public Iterator<Levy> iterator() {
        return new LevytIterator();
    }
    
    
    @SuppressWarnings("unused")
    public Collection<Levy> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if (hk < 0) hk = 0;
        List<Levy> loytyneet = new ArrayList<Levy>();
        for(Levy levy : this) {
            if (WildChars.onkoSamat(levy.anna(hk), ehto)) loytyneet.add(levy);
        }
        
        Collections.sort(loytyneet, new Levy.Vertailija(hk));
        return loytyneet;
    }
    
    
    public Levy annaId(int id) {
        for(Levy levy : this) {
            if (id == levy.getIdNro()) return levy;
        }
        return null;
    }
    
    
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getIdNro()) return i;
        return -1;
    }
    
    
    /**
     * testiohjelma levyille
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Levyt levyt = new Levyt();
        
        Levy radiohead = new Levy(), radiohead2 = new Levy();
        radiohead.rekisteroi();
        radiohead.defaultLevy();
        radiohead2.rekisteroi();
        radiohead2.defaultLevy();
        
        try {
            levyt.lisaa(radiohead);
            levyt.lisaa(radiohead2);
            
            System.out.println("============= Levyt testi =============");
            
            int i = 0;
            for (Levy levy: levyt) {
                System.out.println("Levy nro: " + i);
                levy.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
    
    }
}
