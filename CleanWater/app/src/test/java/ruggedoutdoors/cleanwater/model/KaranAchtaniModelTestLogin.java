package ruggedoutdoors.cleanwater.model;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

/**
 * Created by karanachtani on 4/9/17.
 */

public class KaranAchtaniModelTestLogin {
    Model model;

    @Before
    public void setUp() throws Exception {
        model = Model.getInstance();
        model.addUser("test", "er", "tester", "12345", "t@t.com", "123", "01/02/1995", "test st", "ADMIN");
    }

    @Test
    public void testUserExists() {
        assertEquals(model.logIn("tester", "12345"), true);
        assertEquals(model.getUsername(), "tester");
    }

    @Test
    public void testUsernameInvalid() {
        boolean exceptionThrown = false;
        try {
            model.logIn("abc", "");
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assertEquals(exceptionThrown, true);
    }

    @Test
    public void passwordInvalid() {
        boolean exceptionThrown = false;
        try {
            model.logIn("tester", "");
        } catch (InvalidParameterException e) {
            exceptionThrown = true;
        }
        assertEquals(exceptionThrown, true);
    }
}
