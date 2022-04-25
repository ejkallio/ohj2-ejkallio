package LP.test;
// Generated by ComTest BEGIN
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
import LP.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.07 10:31:46 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KirjastoTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa42 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa42() throws SailoException {    // Kirjasto: 42
    Kirjasto kirjasto = new Kirjasto(); 
    Levy levy1 = new Levy(), levy2 = new Levy(); 
    kirjasto.lisaa(levy1); 
    kirjasto.lisaa(levy2); 
    kirjasto.lisaa(levy1); 
    Collection<Levy> loytyneet = kirjasto.etsi("",-1); 
    Iterator<Levy> it = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 51", levy1, it.next()); 
    assertEquals("From: Kirjasto line: 52", levy2, it.next()); 
    assertEquals("From: Kirjasto line: 53", levy1, it.next()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaGenret102 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaGenret102() throws SailoException {    // Kirjasto: 102
    Kirjasto kirjasto = new Kirjasto(); 
    Levy levy1 = new Levy(), levy2 = new Levy(), levy3 = new Levy(); 
    levy1.rekisteroi(); levy2.rekisteroi(); levy3.rekisteroi(); 
    int id1 = levy1.getIdNro(); 
    int id2 = levy2.getIdNro(); 
    Genre gen1 = new Genre(id1); kirjasto.lisaa(gen1); 
    Genre gen2 = new Genre(id1); kirjasto.lisaa(gen2); 
    Genre gen3 = new Genre(id2); kirjasto.lisaa(gen3); 
    Genre gen4 = new Genre(id2); kirjasto.lisaa(gen4); 
    Genre gen5 = new Genre(id2); kirjasto.lisaa(gen5); 
    List<Genre> loytyneet; 
    loytyneet = kirjasto.annaGenret(levy3); 
    assertEquals("From: Kirjasto line: 119", 0, loytyneet.size()); 
    loytyneet = kirjasto.annaGenret(levy1); 
    assertEquals("From: Kirjasto line: 121", 2, loytyneet.size()); 
    assertEquals("From: Kirjasto line: 122", true, loytyneet.get(0) == gen1); 
    assertEquals("From: Kirjasto line: 123", true, loytyneet.get(1) == gen2); 
    loytyneet = kirjasto.annaGenret(levy2); 
    assertEquals("From: Kirjasto line: 125", 3, loytyneet.size()); 
    assertEquals("From: Kirjasto line: 126", true, loytyneet.get(0) == gen3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta153 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta153() throws SailoException {    // Kirjasto: 153
    Kirjasto kirjasto = new Kirjasto(); 
    Levy levy1 = new Levy(); levy1.defaultLevy(); levy1.rekisteroi(); 
    Levy levy2 = new Levy(); levy2.defaultLevy(); levy2.rekisteroi(); 
    Genre gen1 = new Genre(); gen1.defaultGenre(levy2.getIdNro()); 
    Genre gen2 = new Genre(); gen2.defaultGenre(levy1.getIdNro()); 
    Genre gen3 = new Genre(); gen3.defaultGenre(levy2.getIdNro()); 
    Genre gen4 = new Genre(); gen4.defaultGenre(levy1.getIdNro()); 
    Genre gen5 = new Genre(); gen5.defaultGenre(levy2.getIdNro()); 
    String hakemisto = "testikirjasto"; 
    File dir = new File(hakemisto); 
    File ftied = new File(hakemisto+"/levyt.dat"); 
    File fhtied = new File(hakemisto+"/genret.dat"); 
    dir.mkdir(); 
    ftied.delete(); 
    fhtied.delete(); 
    try {
    kirjasto.lueTiedostosta(hakemisto); 
    fail("Kirjasto: 175 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    kirjasto.lisaa(levy1); 
    kirjasto.lisaa(levy2); 
    kirjasto.lisaa(gen1); 
    kirjasto.lisaa(gen2); 
    kirjasto.lisaa(gen3); 
    kirjasto.lisaa(gen4); 
    kirjasto.lisaa(gen5); 
    kirjasto.tallenna(); 
    kirjasto = new Kirjasto(); 
    kirjasto.lueTiedostosta(hakemisto); 
    Collection<Levy> kaikki = kirjasto.etsi("",-1); 
    Iterator<Levy> it = kaikki.iterator(); 
    assertEquals("From: Kirjasto line: 188", levy1, it.next()); 
    assertEquals("From: Kirjasto line: 189", levy2, it.next()); 
    assertEquals("From: Kirjasto line: 190", false, it.hasNext()); 
    List<Genre> loytyneet = kirjasto.annaGenret(levy1); 
    Iterator<Genre> ih = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 193", gen2, ih.next()); 
    assertEquals("From: Kirjasto line: 194", gen4, ih.next()); 
    assertEquals("From: Kirjasto line: 195", false, ih.hasNext()); 
    loytyneet = kirjasto.annaGenret(levy2); 
    ih = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 198", gen1, ih.next()); 
    assertEquals("From: Kirjasto line: 199", gen3, ih.next()); 
    assertEquals("From: Kirjasto line: 200", gen5, ih.next()); 
    assertEquals("From: Kirjasto line: 201", false, ih.hasNext()); 
    kirjasto.lisaa(levy2); 
    kirjasto.lisaa(gen5); 
    kirjasto.tallenna(); 
    assertEquals("From: Kirjasto line: 205", true, ftied.delete()); 
    assertEquals("From: Kirjasto line: 206", true, fhtied.delete()); 
    File fbak = new File(hakemisto+"/levyt.bak"); 
    File fhbak = new File(hakemisto+"/genret.bak"); 
    assertEquals("From: Kirjasto line: 209", true, fbak.delete()); 
    assertEquals("From: Kirjasto line: 210", true, fhbak.delete()); 
    assertEquals("From: Kirjasto line: 211", true, dir.delete()); 
  } // Generated by ComTest END
}