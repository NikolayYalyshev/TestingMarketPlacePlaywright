package store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.login.LoginPage;


public class LoginTest extends BaseTest {

    @Feature("Тесты страницы Авторизации")
    @DisplayName("Авторизация на сайте")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutAuthorization")
    void successLoginPage(String email, String pass){
            new LoginPage(page)
                    .open()
                    .loginSuccess(email,pass);
    }


    @Feature("Тесты страницы Авторизации")
    @DisplayName("попытка авторизоваться с некорректными данными")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutAuthorizationFail")
    public void testAuthorizationFail(String email, String pass) {
        new LoginPage(page)
                .open()
                .loginNegative(email, pass)
                .checkErrorMessageVisible("неверный email или пароль");
    }

    @Feature("Тесты страницы Авторизации")
    @DisplayName("Регистрация на сайте")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutRegistration")
    public void testRegistration(String personName, String email, String pass){
        new LoginPage(page)
                .open()
                .loginSuccess(personName, email, pass);
    }

}
