package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public abstract class BasePage {

    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected abstract Locator marker();

    protected Locator byTest(String id){
        return page.getByTestId(id);
    }

    protected final void checkOpen(){
        assertThat(marker()).isVisible();
    }
}
