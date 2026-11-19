package pages.catalog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Step;

import pages.BasePage;
import pages.cart.CartPage;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class CatalogPage extends BasePage {

    private final FilterComponent filterComponent;
    private final Locator productElements;

    public CatalogPage(Page page) {
        super(page);
        this.filterComponent = new FilterComponent(byTest("catalog-filters"), this);
        this.productElements = page.getByTestId("product-card");
    }
    @Override
    protected Locator marker(){
    return byTest("catalog-filters");
    }

    public FilterComponent filters() {
        return filterComponent;
    }

    @Step("Проверяем, что страница каталога загрузилась")
    public CatalogPage opened(){
        checkOpen();
        return this;
    }

    @Step("Выбираем товар в корзине")
    public ProductCardComponent getFirstCard(){
        assertThat(productElements.first()).isVisible();
        return new ProductCardComponent(byTest("product-card").first(), this);
    }

    @Step("Проверяем что счетчик товаров в корзине изменился на {count}")
    public CatalogPage checkCartCounterText(String count){
        assertThat(productElements).hasText(count);
        return this;
    }

    @Step("Проверяем, что все товары на странице находятся в наличии (data-stock > 0)")
    public CatalogPage checkAllProductsAreInStock() {
        assertThat(productElements.first()).isVisible();

        for (Locator card : byTest("product-card").all()) {
            Locator stockElement = card.getByTestId("product-stock");
            assertThat(stockElement).hasAttribute("data-stock", Pattern.compile("^[1-9]\\d*$"));
        }

        return this;
    }

    @Step("Выбираем сортировку из фильтра")
    public CatalogPage selectSortingByValue(String value) {
        filterComponent.getSelectSorted().selectOption(value);
        return this;
    }

    @Step("Проверяем сортировку цен по возрастанию")
    public CatalogPage checkPricesSortedAscending() {
        verifyPricesOrder(Comparator.naturalOrder(), "Цены не отсортированы по возрастанию");
        return this;
    }

    @Step("Проверяем сортировку цен по убыванию")
    public CatalogPage checkPricesSortedDescending() {
        verifyPricesOrder(Comparator.reverseOrder(), "Цены не отсортированы по убыванию");
        return this;
    }

    @Step("Проверяем сортировку имен от A до Z")
    public CatalogPage checkNamesSortedAscending() {
        verifyNamesOrder(Comparator.naturalOrder(), "Имена не отсортированы A-Z");
        return this;
    }

    @Step("Проверяем сортировку имен от Z до A")
    public CatalogPage checkNamesSortedDescending() {
        verifyNamesOrder(Comparator.reverseOrder(), "Имена не отсортированы Z-A");
        return this;
    }

    // --- Приватные вспомогательные методы (избавляют от копипаста) ---

    private void verifyPricesOrder(Comparator<Double> comparator, String errorMessage) {
        assertThat(productElements.first()).isVisible(); // Аналог sizeGreaterThan(0)
        Locator priceLocator = productElements.getByTestId("product-price");

        try {
            page.waitForCondition(() -> {
                List<Double> actualPrices = priceLocator.allInnerTexts().stream()
                        .map(text -> text.replaceAll("[^0-9.]", ""))
                        .map(clean -> clean.isEmpty() ? 0.0 : Double.parseDouble(clean))
                        .collect(Collectors.toList());

                if (actualPrices.isEmpty()) return false;

                List<Double> expectedPrices = actualPrices.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());

                return actualPrices.equals(expectedPrices);
            }, new Page.WaitForConditionOptions().setTimeout(5000));
        } catch (TimeoutError e) {
            throw new AssertionError(errorMessage + ". Текущие цены на странице: " + priceLocator.allInnerTexts(), e);
        }
    }

    private void verifyNamesOrder(Comparator<String> comparator, String errorMessage) {
        assertThat(productElements.first()).isVisible();
        Locator nameLocator = productElements.getByTestId("product-name");
        try {
            page.waitForCondition(() -> {
                List<String> actualNames = nameLocator.allInnerTexts().stream()
                        .map(String::trim)
                        .collect(Collectors.toList());
                if (actualNames.isEmpty()) return false;
                List<String> expectedNames = actualNames.stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());

                return actualNames.equals(expectedNames);
            }, new Page.WaitForConditionOptions().setTimeout(5000));
        } catch (TimeoutError e) {
            throw new AssertionError(errorMessage + ". Текущие названия на странице: " + nameLocator.allInnerTexts(), e);
        }
    }

    @Step("Проверяем, что у каждого товара загрузилась валидная картинка")
    public CatalogPage checkAllProductsHaveValidImages() {
        assertThat(productElements.first()).isVisible();
        for (Locator card : productElements.all()) {
            Locator img = card.getByTestId("product-image");
            img.scrollIntoViewIfNeeded();
            assertThat(img).hasJSProperty("complete", true);
            assertThat(img).not().hasJSProperty("naturalWidth", 0);
        }
        return this;
    }

    @Step("Переходим в корзину товаров")
    public CartPage goToCartPage(){
        byTest("nav-cart").click();
        return new CartPage(page).opened();
    }

    @Step("Добавляем первый товар в корзину")
    public CatalogPage addFirstAvailableToCart() {
        getFirstCard().clickBuyButton();
        return this;
    }

    @Step("Проверяем, что количество товаров в корзине больше {minCount}")
    public CatalogPage checkCartCounterIsGreaterThan() {
        assertThat(byTest("cart-badge")).hasText(Pattern.compile(".*\\b[1-9]\\d*\\b.*"));
        return this;
    }

}
