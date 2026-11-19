package store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.catalog.CatalogPage;
import pages.login.LoginPage;

public class CartTest extends BaseTest{

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что страница корзины доступна")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNavigatingCartTab(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .goToCartPage().opened();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что в корзине есть добавленный товар из каталога")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void productToCart(String email, String pass){
       CatalogPage catalogPage = new LoginPage(page)
                .open()
                .loginSuccess(email,pass);
              String nameProduct = catalogPage.getFirstCard()
                .getProductName();
                catalogPage
                        .addFirstAvailableToCart()
                        .goToCartPage()
                        .checkProductIsPresentInCart(nameProduct);
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что из корзины удаляются товары")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void deleteProductInCart(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .removeAllItemsFromCart();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что корзина очищается")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void clearProductInCart(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .clickButtonClear().checkCartIsEmpty();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Добавляем промокод к товарам в корзине")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsApplyPromocode")
    public void testApplyPromocode(String email, String pass, String promo){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .promoApply(promo)
                .checkPromoCodeApplied(promo);
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Добавляем невалидный промокод к товарам в корзине")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsPromocodeNotApply")
    public void testNegativeInvalidPromocodeNotApply(String email, String pass, String promo){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .promoApply(promo)
                .checkPromoCodeNotApplied();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем доступность перехода на страницу доставки")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNavigatingDeliveryTab(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .goToCheckout().opened();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем невозможность перехода на страницу доставки, если корзина пуста")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNegativeCheckoutButtonIsDisabledWhenCartIsEmpty(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .goToCartPage().opened()
                .checkCheckoutButtonIsDisabled();
    }
}
