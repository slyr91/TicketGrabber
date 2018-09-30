/*
Arouchian, Daryl
9/26/2018
*/
package daryl.ticketgrabber.workers;

import daryl.ticketgrabber.data.EntryDataTVTickets;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

class WebDriverServiceImpl implements WebDriverService {

    private WebDriver browser;
    private String currentURL;
    private BrowserMobProxy server = new BrowserMobProxyServer();
    private ChromeOptions options = new ChromeOptions();
    private Proxy proxy;
    private String user = System.getProperty("user.name");

    WebDriverServiceImpl(String url) {
        server.start();
        server.addResponseFilter((response, contents, messageInfo) -> System.out.println(response.getStatus().code()));
        proxy = ClientUtil.createSeleniumProxy(server);
        options.setCapability(CapabilityType.PROXY, proxy);
//        options.setHeadless(true);
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\" + user + "\\Downloads\\chromedriver_win32\\chromedriver.exe");
        browser = new ChromeDriver(options);
        browser.get(url);
    }

    @Override
    public SimpleWebResult navigateTo(String url) {
        currentURL = url;
        browser.get(url);
        //return OK for now
        return SimpleWebResult.OK;
    }

    @Override
    public SimpleWebResult refreshPage() {
        return navigateTo(currentURL);
    }

    @Override
    public boolean lookForShow(String show) {
        return false;
    }

    @Override
    public boolean enterInformationAndSubmit(EntryDataTVTickets entry) {
        return false;
    }

    @Override
    public boolean terminateSession() {
        return false;
    }
}
