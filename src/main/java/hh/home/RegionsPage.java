package hh.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class RegionsPage {
    public WebDriver driver;
    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
    public RegionsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //========================Элементы страницы===================================

    //Список с названием городов в регионе Россия
    @FindBy(xpath = "//div[@class = 'area-switcher-column']/ul")
    public WebElement listWithCityNamesUl;

    //Строка поиска города
    @FindBy(id = "area-search-input")
    public WebElement searchCityString;

    //=======================Методы работы с элементами страницы===================

    //Выбрать нужный город
    public boolean setCityName(String cityName) {
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.visibilityOf(searchCityString));
        //Список ссылок на города регионов
        List<WebElement> listWithCityNames = listWithCityNamesUl.findElements(By.tagName("a"));
        System.out.println("Количество ссылок с городами" + listWithCityNames.size());
        //Список имен городов
        List<String> cityNames = new ArrayList<String>(100);
        for (int i = 0; i < listWithCityNames.size(); i++){
            cityNames.add(listWithCityNames.get(i).getText());
        }
        //Индекс ссылки заданного города в списке
        int index = cityNames.indexOf(cityName);
        if (index >= 0) {
            listWithCityNames.get(index).click();
            return true;
        }
        return false;
    }
}
