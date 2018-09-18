package daryl.ticketgrabber.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class FormDataTest {

    private FormData formData;

    @Before
    public void setUp() throws Exception {
        formData = new FormData(ClassLoader.getSystemResources("sampledata.properties").nextElement().getFile());
    }

    @After
    public void tearDown() throws Exception {
        formData = null;
    }

    @Test(expected = InvalidFileExtensionException.class)
    public void initInvalidTest() throws InvalidFileExtensionException {
        FormData invalid = new FormData("invalid.txt");
        try {
            invalid.init();
        } catch (IOException e) {
            fail("Should not have gotten pass the file extension check.");
        }
        fail("The FormData init() method succeeded where it should have failed.");
    }

    @Test(expected = FileNotFoundException.class)
    public void initWrongFileTest() throws IOException {
        FormData invalid = new FormData("wrongfile.properties");
        try {
            invalid.init();
        } catch (InvalidFileExtensionException e) {
            fail("The file extension is correct.");
        }
        fail("The finished initializing when it should have failed.");
    }

    @Test
    public void initTest() {
        try {
            formData.init();
        } catch (IOException e) {
            fail("The file does exist.");
        } catch (InvalidFileExtensionException e) {
            fail("The file does have the correct extension.");
        }

        assertEquals("bob", formData.getEntries().get(0).getFirstName());
    }

    @Test
    public void getEntriesTest() {
        try {
            formData.init();
        } catch (IOException e) {
            fail("The file does exist.");
        } catch (InvalidFileExtensionException e) {
            fail("The file does have the correct extension.");
        }

        assertEquals("1", formData.getEntries().get(0).getNumberOfTickets());
        assertEquals("bob", formData.getEntries().get(0).getFirstName());
        assertEquals("smith", formData.getEntries().get(0).getLastName());
        assertEquals("3235551234", formData.getEntries().get(0).getPhoneNumber());
        assertEquals("bob@smith.com", formData.getEntries().get(0).getEmail());
        assertEquals("something", formData.getEntries().get(0).getHowDidYouFindUs());

    }
}