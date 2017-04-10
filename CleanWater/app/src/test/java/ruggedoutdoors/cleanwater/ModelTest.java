package ruggedoutdoors.cleanwater;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

import ruggedoutdoors.cleanwater.model.Model;

/**
 * Created by Austin Dunn on 4/9/2017.
 */

public class ModelTest {
    /**
     * The test fixture
     */
    private Model model;

    /**
     * This method is run before each test
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        model = Model.getInstance();
    }

    /**
     * Test method for {@link
     * ruggedoutdoors.cleanwater.model.Model#logIn(String username, String password)}.
     */
    @Test
    public void testlogIn() {
        try {
            model.logIn("pablo", "12345");
        } catch (Exception e) {
            Assert.fail("Exception should not have been thrown here");
        }

        try {
            model.logIn("invalid", "89237424");
            Assert.fail("Exception should have been thrown here for no user found");
        } catch (NoSuchElementException e) {

        }

        try {
            model.logIn("pablo", "89237424");
            Assert.fail("Exception should have been thrown here for invalid password");
        } catch (InvalidParameterException e) {

        }
    }

}
