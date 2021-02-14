package hh.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StartPage {

    public WebDriver driver;

    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
    public StartPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //Элементы страницы

    //Строка ввода критерия поика
    @FindBy(xpath = "//input[@name = 'text']")
    public WebElement searchString;

    //Кнопка поиска "Найти"
    @FindBy(xpath = "//span[text()='Найти']")
    public WebElement searchButton;

    //Кнопка выбора города региона
    @FindBy(xpath = "//button[@data-qa = 'mainmenu_areaSwitcher']/div")
    public WebElement selectRegionButton;
    //@FindBy(xpath = "//span[text()='Выбрать другой']")
    //public WebElement selectRegionButton;


    //Методы работы с элементами страницы

    //Ввод текста в строку поиска
    public void inputTextInSearchString(String text){
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(searchString));
        searchString.click();
        searchString.sendKeys(text);
    }

    //Нажать кнопку "Найти"
    public  void clickSearchButton() {
        searchButton.click();
    }

    //Получить имя текущего города региона
    public String getRegionCityName() {
        (new WebDriverWait(driver,10))
                .until(ExpectedConditions.elementToBeClickable(searchString));
        return selectRegionButton.getText();
    }

    //Нажать кнопку для выбора города региона
    public void clickSelectRegionButton() {
        selectRegionButton.click();
    }

}
