package jp.ac.titech.itpro.sdl.hilbert;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void checkParamOfBitmapManager(){
        MainActivity activity = new MainActivity();
        BitmapManager manager = new BitmapManager(9);
        assertFalse(manager.wasDrawn(0));
    }
}
