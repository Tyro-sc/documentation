package sc.tyro.doc

import io.github.bonigarcia.wdm.WebDriverManager
import io.javalin.Javalin
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import sc.tyro.web.WebBundle

import static io.javalin.http.staticfiles.Location.CLASSPATH
import static org.openqa.selenium.remote.BrowserType.CHROME
import static org.openqa.selenium.remote.BrowserType.FIREFOX

class TyroExtension implements BeforeAllCallback, AfterAllCallback {
    public static String BASE_URL
    private static Javalin app
    private static WebDriver webDriver

    @Override
    void beforeAll(ExtensionContext extensionContext) throws Exception {
        app = Javalin.create({it.staticFiles.add "/webapp", CLASSPATH }).start(0)

        BASE_URL = "http://localhost:${app.port()}"

        // Add -DbrowserType=firefox/chrome/... to you VM Option to select the browser
        String browser = System.getProperty("browserType")
        if (!browser) {
            println "No browser selected. Use Chrome"
            browser = FIREFOX
        }

        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup()
                FirefoxOptions options = new FirefoxOptions()
                options.setHeadless(true)
                webDriver = new FirefoxDriver(options)
                break
            case CHROME:
                WebDriverManager.chromedriver().setup()
                ChromeOptions options = new ChromeOptions()
                options.setHeadless(true)
                webDriver = new ChromeDriver()
                break
        }
        WebBundle.init(webDriver)
    }

    @Override
    void afterAll(ExtensionContext extensionContext) throws Exception {
        webDriver.quit()
        app.stop()
    }
}
