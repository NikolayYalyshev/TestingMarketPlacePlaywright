package pages.catalog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class ProductCardComponent {

    private final Locator root;

    private final CatalogPage parentPage;

    public ProductCardComponent(Locator root, CatalogPage parentPage) {
        this.root = root;
        this.parentPage = parentPage;
    }

    @Step("Нажимаем кнопку 'Купить' в карточке товара")
    public CatalogPage clickBuyButton() {
        root.getByTestId("add-to-cart").click();
        return parentPage;
    }

    public String getProductName() {
        return root.getByTestId("product-name").innerText();
    }
}
