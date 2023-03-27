package sc.tyro.doc

import io.github.bonigarcia.wdm.WebDriverManager
import io.javalin.Javalin
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.Logger
import sc.tyro.web.WebBundle

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver
import static io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver
import static io.javalin.http.staticfiles.Location.CLASSPATH
import static java.lang.Boolean.valueOf
import static java.lang.System.getenv
import static java.net.InetAddress.getByName
import static org.slf4j.LoggerFactory.getLogger

class TyroExtension implements BeforeAllCallback, AfterAllCallback {
    private static Logger LOGGER = getLogger(TyroExtension)

    public static String BASE_URL
    private static Javalin app
    private static WebDriver webDriver
    private static WebDriverManager wdm
    private boolean isCI = valueOf(getenv('CI'))

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        app = Javalin.create({it.staticFiles.add "/webapp", CLASSPATH }).start(0)

        DatagramSocket socket = new DatagramSocket()
        socket.connect(getByName("8.8.8.8"), 10002)
        String host_ip = socket.localAddress.hostAddress
        BASE_URL = "http://${host_ip}:${app.port()}/"

        // Add -Dbrowser=firefox/chrome/... to you VM Option to select the browser
        String browser = System.getProperty("browser")
        if (!browser) {
            LOGGER.info('No Browser selected. Use Chrome')
            browser = "chrome"
        }

        System.setProperty("webdriver.http.factory", "jdk-http-client")
        switch (browser) {
            case "firefox":
                wdm = firefoxdriver()
                FirefoxOptions options = new FirefoxOptions()
                wdm.capabilities(options)
                break
            case "chrome":
                wdm = chromedriver()
                ChromeOptions options = new ChromeOptions()
                wdm.capabilities(options)
                break
            default:
                throw new IllegalStateException("Fail to set browser: " + browser)
        }

        if (isCI) {
            wdm.browserInDocker().enableVnc()
        }

        webDriver = wdm.create()
        WebBundle.init(webDriver)
    }

    @Override
    void afterAll(ExtensionContext extensionContext) throws Exception {
        webDriver.quit()
        wdm.quit()
        app.stop()
    }
}
