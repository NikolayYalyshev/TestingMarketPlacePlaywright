package store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.cart.CartPage;
import pages.catalog.CatalogPage;
import pages.delivery.DeliveryPage;
import pages.login.LoginPage;

public class E2ETest extends BaseTest{
    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность добавления товара в корзину и его заказа")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsE2EDeliveryProduct")
    public void testLoginSelectItemGoCartChooseDelivery(String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)
                .addFirstAvailableToCart()
                .goToCartPage().opened()
                .goToCheckout().opened();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность добавления товара в корзину и его заказа")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsVariousFilters")
    public void selectProductsVariousFilters(String email, String pass, String categoryFirstProd,
                                             String brandFirstProd, String categorySecondProd, String categoryThirdProd,
                                             String brandThirdProd, String minPrice, String maxPrice){
        new LoginPage(page)
                .open()
                .loginSuccess(email,pass)

        // 3. Добавляем 1-й товар (Категория 1 + Бренд 1)
                .filters().selectCategory(categoryFirstProd)
                .filters().selectBrand(brandFirstProd)
                .addFirstAvailableToCart()

                .filters().clickAllCategory()
                .filters().clickAllBrand()

        // 4. Добавляем 2-й товар (Категория 2)
        .filters().selectCategory(categorySecondProd)
                .addFirstAvailableToCart()

        .filters().clickAllCategory()

        // 5. Добавляем 3-й товар (Категория 3 + Бренд 3 + Диапазон цен)
                .filters().selectCategory(categoryThirdProd)
                .filters().selectBrand(brandThirdProd)
                .filters().selectMinPrice(minPrice)
                .filters().selectMaxPrice(maxPrice)
                .addFirstAvailableToCart()

                .checkCartCounterIsGreaterThan();
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность заказать товар со скидкой")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsGetProductsAtDiscount")
    public void personCanGetProductsAtDiscount(String email, String pass, String categoryProd,
                                               String brandFProd, String promo){
        CartPage cartPage = new LoginPage(page)
                .open()
                .loginSuccess(email,pass)

                // 3. Добавляем 1-й товар (Категория 1 + Бренд 1)
                .filters().selectCategory(categoryProd)
                .filters().selectBrand(brandFProd)
                .addFirstAvailableToCart()
                .goToCartPage().opened();

        double priceBeforeDiscount = cartPage.getTotalPriceInCart();
        cartPage.promoApply(promo).checkPromoCodeApplied(promo);


         cartPage.checkPriceDecreasedAfterPromo(priceBeforeDiscount);
        double priceWithDiscount = cartPage.getTotalPriceInCart();
        DeliveryPage deliveryPage = cartPage.goToCheckout().opened();
        deliveryPage.checkPriceMatchesCart(priceWithDiscount);

    }
}
