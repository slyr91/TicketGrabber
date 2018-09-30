package daryl.ticketgrabber.workers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebDriverServiceImplTest {

    private WebDriverServiceImpl webDriverService;
    private String url = "https://www.google.com";

    @Before
    public void setUp() throws Exception {
        webDriverService = new WebDriverServiceImpl(url);

    }

    @After
    public void tearDown() throws Exception {
        webDriverService = null;
    }

    @Test
    public void navigateTo() {
        assertEquals(SimpleWebResult.OK, webDriverService.navigateTo(url));
    }

    @Test
    public void refreshPage() {
    }

    @Test
    public void lookForShow() {
    }

    @Test
    public void enterInformationAndSubmit() {
    }

    @Test
    public void terminateSession() {
    }
}