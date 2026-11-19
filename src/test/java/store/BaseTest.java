package store;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

@UsePlaywright(BaseTest.Config.class)
public class BaseTest {

    protected Page page;

    @BeforeEach
    void initPage(Page page) {
        this.page = page;
    }

    public static class Config implements OptionsFactory {
        @Override
        public Options getOptions(){
            return new Options()
                    .setHeadless(false)
                    .setLaunchOptions(new BrowserType.LaunchOptions()
                            .setArgs(List.of("--start-maximized")))
                    .setContextOptions(new Browser.NewContextOptions()
                            .setBaseURL("http://localhost:8081").setViewportSize(null));
            //.setTrace(Options.Trace.RETAIN_ON_FAILURE).setOutputDir(Paths.get("build/playwright-traces"));
        }
    }
}
