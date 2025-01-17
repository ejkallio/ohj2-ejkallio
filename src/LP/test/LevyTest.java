package LP.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import LP.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.28 14:01:39 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class LevyTest {



  // Generated by ComTest BEGIN
  /** testGetNimi99 */
  @Test
  public void testGetNimi99() {    // Levy: 99
    Levy levy = new Levy(); 
    levy.defaultLevy(); 
    assertEquals("From: Levy line: 102", "In Rainbows", levy.getNimi()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta147 */
  @Test
  public void testAseta147() {    // Levy: 147
    Levy levy = new Levy(); 
    assertEquals("From: Levy line: 149", null, levy.aseta(1, "In Rainbows")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi246 */
  @Test
  public void testRekisteroi246() {    // Levy: 246
    Levy levy1 = new Levy(); 
    assertEquals("From: Levy line: 248", 0, levy1.getIdNro()); 
    levy1.rekisteroi(); 
    Levy levy2 = new Levy(); 
    levy2.rekisteroi(); 
    int n1 = levy1.getIdNro(); 
    int n2 = levy2.getIdNro(); 
    assertEquals("From: Levy line: 254", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString288 */
  @Test
  public void testToString288() {    // Levy: 288
    Levy levy = new Levy(); 
    levy.parse(" 1 | In Rainbows | Radiohead"); 
    assertEquals("From: Levy line: 291", true, levy.toString().startsWith("1|In Rainbows|Radiohead|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse312 */
  @Test
  public void testParse312() {    // Levy: 312
    Levy levy = new Levy(); 
    levy.parse(" 1 | In Rainbows | Radiohead"); 
    assertEquals("From: Levy line: 315", true, levy.toString().startsWith("1|In Rainbows|Radiohead|")); 
    levy.rekisteroi(); 
    int n = levy.getIdNro(); 
    levy.parse(""+(n+20)); 
    levy.rekisteroi(); 
    assertEquals("From: Levy line: 321", n+20+1, levy.getIdNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEquals344 */
  @Test
  public void testEquals344() {    // Levy: 344
    Levy levy1 = new Levy(); 
    levy1.parse(" 1 | In Rainbows | Radiohead"); 
    Levy levy2 = new Levy(); 
    levy2.parse(" 1 | In Rainbows | Radiohead"); 
    Levy levy3 = new Levy(); 
    levy3.parse(" 2 | Madvillainy | Madvillain"); 
    assertEquals("From: Levy line: 352", true, levy1.equals(levy2)); 
    assertEquals("From: Levy line: 353", false, levy2.equals(levy3)); 
  } // Generated by ComTest END
}