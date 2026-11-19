package pages.delivery;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Step;
import pages.BasePage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DeliveryPage extends BasePage {

    public DeliveryPage(Page page) {
        super(page);
    }

    @Override
    protected Locator marker() {
        return byTest("checkout-to-payment");
    }

    @Step("Проверяем, что страница доставки загрузилась")
    public DeliveryPage opened(){
        checkOpen();
        return this;
    }

    @Step("Проверяем, что стоимость на странице доставки совпадает с ценой из корзины ({expectedPrice})")
    public DeliveryPage checkPriceMatchesCart(double expectedPrice) {
        Locator totalPriceInDelivery = byTest("checkout-subtotal");
        assertThat(totalPriceInDelivery).isVisible();

        try {
            page.waitForCondition(() -> {
                String currentPriceText = totalPriceInDelivery.innerText().replaceAll("[^0-9.]", "");
                double currentPrice = currentPriceText.isEmpty() ? 0.0 : Double.parseDouble(currentPriceText);
                return Double.compare(currentPrice, expectedPrice) == 0;
            }, new Page.WaitForConditionOptions().setTimeout(5000));
        } catch (TimeoutError e) {
            throw new AssertionError(
                    "Цена на странице доставки не совпадает с корзиной. Ожидалось: " + expectedPrice +
                            ", текущее значение на экране: '" + totalPriceInDelivery.innerText() + "'", e
            );
        }

        return this;
    }

}
