package pages.catalog;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;


public class FilterComponent {

    private final Locator root;

    private final CatalogPage parentPage;

    public FilterComponent(Locator root, CatalogPage parentPage) {
        this.root = root;
        this.parentPage = parentPage;
    }

    private void selectFilterOption (String filterType, String optionName) {
        root.locator("[data-filter-type='%s']".formatted(filterType))
                .filter(new Locator.FilterOptions().setHasText(optionName))
                .click();
    }

    @Step("Выбираем категорию: {category}")
    public CatalogPage selectCategory(String category) {
        selectFilterOption("category", category);
        return parentPage;
    }

    @Step("Выбираем бренд: {brand}")
    public CatalogPage selectBrand(String brand) {
        selectFilterOption("brand", brand);
        return parentPage;
    }

    @Step("Вводим минимальную сумму")
    public CatalogPage selectMinPrice(String minPrice){
        root.getByTestId("price-min").fill(minPrice);
        return parentPage;
    }

    @Step("Вводим максимальную сумму сумму")
    public CatalogPage selectMaxPrice(String maxPrice){
        root.getByTestId("price-min").fill(maxPrice);
        return parentPage;
    }

    @Step("Устанавливаем признак 'Только в наличии'")
    public CatalogPage setCheckboxStoke(){
        root.getByTestId("in-stock-checkbox").click();
        return parentPage;
    }

    @Step("В фильтре выбираем признак сортировки")
    public Locator getSelectSorted() {
        return root.getByTestId("catalog-sort");
    }

    @Step("Выбираем Все категории")
    public CatalogPage clickAllCategory(){
        root.getByTestId("filter-category-all").click();
        return parentPage;
    }

    @Step("Выбираем все бренды")
    public CatalogPage clickAllBrand(){
        root.getByTestId("filter-all").click();
        return parentPage;
    }
}
