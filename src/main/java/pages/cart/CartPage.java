package pages.cart;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.delivery.DeliveryPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage extends BasePage {

    private final Locator removeButtons;
    public CartPage(Page page) {
        super(page);
        this.removeButtons = page.getByTestId("remove-item");
    }

    @Override
    protected Locator marker() {
        return byTest("checkout-button");
    }

    @Step("Проверяем, что страница корзины загрузилась")
    public CartPage opened(){
        checkOpen();
        return this;
    }

    @Step("Проверяем, что в корзине есть товар с именем '{expectedName}'")
    public CartPage checkProductIsPresentInCart(String expectedName) {
        assertThat(byTest("cart-item-name")).hasText(new String[]{expectedName});
        return this;
    }

    @Step("Удаляем все товары через карточки товаров в корзине")
    public CartPage removeAllItemsFromCart() {
        while (removeButtons.count() > 0) {
            int previousSize = removeButtons.count();
            removeButtons.first().click();

            assertThat(removeButtons).hasCount(previousSize - 1);
        }
        return this;
    }

    @Step("Очищаем корзину полностью")
    public CartPage clickButtonClear(){
        byTest("clear-cart-button").click();
        return this;
    }

    @Step("Проверяем, что корзина стала пустой")
    public CartPage checkCartIsEmpty() {
        assertThat(removeButtons).hasCount(0);
        return this;
    }

    @Step("Вводим промокод: {promocode}")
    public CartPage enterPromoCode(String promocode){
        byTest("promo-input").fill(promocode);
        return this;
    }

    @Step("Применяем промокод")
    public CartPage promoApply(String promocode){
        enterPromoCode(promocode);
        byTest("promo-apply").click();
        return this;
    }

    @Step("Проверяем, что применился именно промокод '{expectedPromo}'")
    public CartPage checkPromoCodeApplied(String expectedPromo) {
        assertThat(byTest("applied-promo")).hasAttribute("data-promo", expectedPromo);
        return this;
    }

    @Step("Проверяем, что промокод не применился (сообщение/плашка отсутствует)")
    public CartPage checkPromoCodeNotApplied() {
        assertThat(byTest("applied-promo")).not().isVisible();
        return this;
    }

    @Step("Нажимаем кнопку оформления заказа")
    public DeliveryPage goToCheckout(){
        byTest("checkout-button").click();
        return new DeliveryPage(page).opened();
    }

    @Step("Убеждаемся, что корзина пуста перед проверкой")
    public CartPage checkCartIsEmptyBeforeTest() {
        assertThat(byTest("cart-item")).hasCount(0);
        return this;
    }

    @Step("Проверяем, что кнопка 'К оформлению' заблокирована (disabled)")
    public CartPage checkCheckoutButtonIsDisabled() {
        checkCartIsEmptyBeforeTest();
        assertThat(byTest("checkout-button")).isDisabled();
        return this;
    }

    @Step("Получаем итоговую стоимость заказа в корзине")
    public double getTotalPriceInCart() {
        String priceText = byTest("cart-total").innerText();
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }

    @Step("Проверяем, что итоговая сумма уменьшилась после применения промокода (старая цена: {priceBeforeDiscount})")
    public CartPage checkPriceDecreasedAfterPromo(double priceBeforeDiscount) {
        Locator totalPrice = byTest("cart-total");
        assertThat(totalPrice).isVisible();

        try {
            page.waitForCondition(() -> {
                String currentPriceText = totalPrice.innerText().replaceAll("[^0-9.]", "");
                double currentPrice = currentPriceText.isEmpty() ? 0.0 : Double.parseDouble(currentPriceText);

                return currentPrice > 0 && currentPrice < priceBeforeDiscount;
            }, new Page.WaitForConditionOptions().setTimeout(5000));
        } catch (TimeoutError e) {
            throw new AssertionError(
                    "Цена не уменьшилась после применения промокода. Было: " + priceBeforeDiscount +
                            ", текущее значение на экране: '" + totalPrice.innerText() + "'", e
            );
        }

        return this;
    }
}
