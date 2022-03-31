package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite kerho-ohjelmalle
 * @author Kivikallio
 * @version 31.3.2022
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    LP.test.LevyTest.class,
    LP.test.LevytTest.class,
    LP.test.GenreTest.class,
    LP.test.GenretTest.class,
    LP.test.KirjastoTest.class
    })

public class AllTests {
    //
}
