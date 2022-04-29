package LP.test;
// Generated by ComTest BEGIN
import LP.SailoException;
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
import LP.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.28 15:36:07 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KirjastoTest {


  // Generated by ComTest BEGIN  // Kirjasto: 15
  private Kirjasto kirjasto; 
  private Levy levy1; 
  private Levy levy2; 
  private int jid1; 
  private int jid2; 
  private Genre gen1; 
  private Genre gen2; 
  private Genre gen3; 
  private Genre gen4; 
  private Genre gen5; 

  public void alustaKirjasto() {
      kirjasto = new Kirjasto(); 
      levy1 = new Levy(); levy1.defaultLevy(); levy1.rekisteroi(); 
      levy2 = new Levy(); levy2.defaultLevy(); levy2.rekisteroi(); 
      jid1 = levy1.getIdNro(); 
      jid2 = levy2.getIdNro(); 
      gen1 = new Genre(jid2); gen1.defaultGenre(jid2); 
      gen2 = new Genre(jid1); gen2.defaultGenre(jid1); 
      gen3 = new Genre(jid2); gen3.defaultGenre(jid2); 
      gen4 = new Genre(jid1); gen4.defaultGenre(jid1); 
      gen5 = new Genre(jid2); gen5.defaultGenre(jid2); 
      try {
      kirjasto.lisaa(levy1); 
      kirjasto.lisaa(levy2); 
      kirjasto.lisaa(gen1); 
      kirjasto.lisaa(gen2); 
      kirjasto.lisaa(gen3); 
      kirjasto.lisaa(gen4); 
      kirjasto.lisaa(gen5); 
      } catch (Exception e) {
       System.err.println(e.getMessage()); 
      }
     }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaGenre83 
   * @throws Exception when error
   */
  @Test
  public void testPoistaGenre83() throws Exception {    // Kirjasto: 83
    alustaKirjasto(); 
    assertEquals("From: Kirjasto line: 86", 2, kirjasto.annaGenret(levy1).size()); 
    kirjasto.poistaGenre(gen2); 
    assertEquals("From: Kirjasto line: 88", 1, kirjasto.annaGenret(levy1).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa101 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa101() throws SailoException {    // Kirjasto: 101
    Kirjasto kirjasto = new Kirjasto(); 
    Levy levy1 = new Levy(), levy2 = new Levy(); 
    kirjasto.lisaa(levy1); 
    kirjasto.lisaa(levy2); 
    kirjasto.lisaa(levy1); 
    Collection<Levy> loytyneet = kirjasto.etsi("",-1); 
    Iterator<Levy> it = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 110", levy1, it.next()); 
    assertEquals("From: Kirjasto line: 111", levy2, it.next()); 
    assertEquals("From: Kirjasto line: 112", levy1, it.next()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa125 
   * @throws SailoException when error
   */
  @Test
  public void testKorvaaTaiLisaa125() throws SailoException {    // Kirjasto: 125
    alustaKirjasto(); 
    assertEquals("From: Kirjasto line: 128", 2, kirjasto.etsi("*",0).size()); 
    kirjasto.korvaaTaiLisaa(levy1); 
    assertEquals("From: Kirjasto line: 130", 2, kirjasto.etsi("*",0).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaGenret179 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaGenret179() throws SailoException {    // Kirjasto: 179
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
    assertEquals("From: Kirjasto line: 196", 0, loytyneet.size()); 
    loytyneet = kirjasto.annaGenret(levy1); 
    assertEquals("From: Kirjasto line: 198", 2, loytyneet.size()); 
    assertEquals("From: Kirjasto line: 199", true, loytyneet.get(0) == gen1); 
    assertEquals("From: Kirjasto line: 200", true, loytyneet.get(1) == gen2); 
    loytyneet = kirjasto.annaGenret(levy2); 
    assertEquals("From: Kirjasto line: 202", 3, loytyneet.size()); 
    assertEquals("From: Kirjasto line: 203", true, loytyneet.get(0) == gen3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta230 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta230() throws SailoException {    // Kirjasto: 230
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
    fail("Kirjasto: 252 Did not throw SailoException");
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
    assertEquals("From: Kirjasto line: 265", levy1, it.next()); 
    assertEquals("From: Kirjasto line: 266", levy2, it.next()); 
    assertEquals("From: Kirjasto line: 267", false, it.hasNext()); 
    List<Genre> loytyneet = kirjasto.annaGenret(levy1); 
    Iterator<Genre> ih = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 270", gen2, ih.next()); 
    assertEquals("From: Kirjasto line: 271", gen4, ih.next()); 
    assertEquals("From: Kirjasto line: 272", false, ih.hasNext()); 
    loytyneet = kirjasto.annaGenret(levy2); 
    ih = loytyneet.iterator(); 
    assertEquals("From: Kirjasto line: 275", gen1, ih.next()); 
    assertEquals("From: Kirjasto line: 276", gen3, ih.next()); 
    assertEquals("From: Kirjasto line: 277", gen5, ih.next()); 
    assertEquals("From: Kirjasto line: 278", false, ih.hasNext()); 
    kirjasto.lisaa(levy2); 
    kirjasto.lisaa(gen5); 
    kirjasto.tallenna(); 
    assertEquals("From: Kirjasto line: 282", true, ftied.delete()); 
    assertEquals("From: Kirjasto line: 283", true, fhtied.delete()); 
    File fbak = new File(hakemisto+"/levyt.bak"); 
    File fhbak = new File(hakemisto+"/genret.bak"); 
    assertEquals("From: Kirjasto line: 286", true, fbak.delete()); 
    assertEquals("From: Kirjasto line: 287", true, fhbak.delete()); 
    assertEquals("From: Kirjasto line: 288", true, dir.delete()); 
  } // Generated by ComTest END
}