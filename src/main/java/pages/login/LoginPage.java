package pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.catalog.CatalogPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }


    @Override
    protected Locator marker(){
        return byTest("auth-submit");
    }


    @Step("Открываем страницу авторизации")
    public LoginPage open(){
        page.navigate("/");
        return opened();
    }

    @Step("Проверяем, что страница авторизации загрузилась")
    public LoginPage opened(){
        checkOpen();
        return this;
    }

    @Step("Авторизуемся пользователем: {username}")
    public void enterData(String email, String password){
        byTest("login-email").fill(email);
        byTest("login-password").fill(password);
        marker().click();
    }

    @Step("Регистрируем пользователя: {username}")
    public void enterData(String name, String  email, String password){
        byTest("tab-register").click();
        byTest("register-name").fill(name);
        byTest("login-email").fill(email);
        byTest("login-password").fill(password);
        marker().click();
    }

    @Step("Авторизация с корректными данными")
    public CatalogPage loginSuccess(String email, String pass){
        enterData(email, pass);
        return new CatalogPage(page).opened();
    }

    @Step("Регистрация на сайте")
    public CatalogPage loginSuccess(String name, String email, String pass){
        enterData(name, email, pass);
        return new CatalogPage(page).opened();
    }

    @Step("Авторизация с некорректными данными")
    public LoginPage loginNegative(String email, String pass){
        enterData(email,pass);
        return this;
    }

    @Step("Проверяем, что отобразилась ошибка авторизации: '{expectedErrorText}'")
    public LoginPage checkErrorMessageVisible(String expectedErrorText) {
        assertThat(byTest("auth-error")).hasText(expectedErrorText);
        return this;
    }
}
