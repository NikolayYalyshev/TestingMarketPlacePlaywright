package store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.catalog.CatalogPage;
import pages.login.LoginPage;

public class CatalogTest extends BaseTest{

    @Feature("Тесты страницы Каталог")
    @DisplayName("Добавление продукта в корзину без перехода")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void addProductToCart(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .getFirstCard()
                .clickBuyButton()
                .checkCartCounterText("1");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по категории")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByCategory")
    public void filteringByCategory(String email, String pass, String category){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .filters()
                .selectCategory(category)
                .addFirstAvailableToCart()
                .checkCartCounterText("1");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по бренду")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByBrand")
    public void filteringByBrand(String email, String pass, String brand){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .filters()
                .selectBrand(brand)
                .addFirstAvailableToCart()
                .checkCartCounterText("1");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по цене")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByPrice")
    public void filterByPrice(String email, String pass, String minPrice, String maxPrice){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .filters().selectMinPrice(minPrice)
                .filters().selectMaxPrice(maxPrice)
                .addFirstAvailableToCart()
                .checkCartCounterText("1");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров только в наличии")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void filterByStokeCheckbox(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .filters().setCheckboxStoke()
                .checkAllProductsAreInStock();
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров через сортировку")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringBySorted")
    public void filterBySorted(String email, String pass, String sortValue){
       CatalogPage catalogPage = new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .selectSortingByValue(sortValue);
        switch (sortValue) {
            case "price_asc":
                catalogPage.checkPricesSortedAscending();
                break;
            case "price_desc":
                catalogPage.checkPricesSortedDescending();
                break;
            case "name_asc":
                catalogPage.checkNamesSortedAscending();
                break;
            case "name_desc":
                catalogPage.checkNamesSortedDescending();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип сортировки: " + sortValue);
        }
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Проверяем наличие картинки у товара")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void testProductsHaveValidImages(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .checkAllProductsHaveValidImages();
    }
}
