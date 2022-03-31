package LP;


/**
 * Kirjaston levyt, joka osaa mm. lisätä uuden levyn
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
public class Levyt {
    public static final int MAX_LEVYJA    = 5;
    private int             lkm           = 0;
    private String          tiedostonNimi = "";
    private Levy           alkiot[]      = new Levy[MAX_LEVYJA];
    
    
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
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = levy;
        lkm++;
    }
    
    
    /**
     * palauttaa viitteen i:teen levyyn
     * @param i monennenko levyn viite halutaan
     * @return viite levyyn jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Levy anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lukee kirjaston tiedostosta, kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/albumit.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa kirjaston tiedostoon, kesken
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
         throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Palauttaa kirjaston levyjen lukumäärän
     * @return levyjen lukumäärä
     */
    public int getLkm() {
        return lkm;
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
            
            for (int i = 0; i < levyt.getLkm(); i++ ) {
                Levy levy = levyt.anna(i);
                System.out.println("Levy nro: " + i);
                levy.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
    
    }
}
