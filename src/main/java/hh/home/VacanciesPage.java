package hh.home;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VacanciesPage {

    public WebDriver driver;

    //Конструктор обращается к классу PageFactory, чтобы заработала аннотация @FindBy
    //Он инициализирует элементы страницы
    public VacanciesPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //======================Элементы страницы========================================

    //Групповой элемент блоков вакансий
    @FindBy(xpath = "//div[@class = 'vacancy-serp']")
    public WebElement groupElementOfVacancies;

    //Кнопка "Списком"
    @FindBy(xpath = "//button[text() = 'Списком']")
    public WebElement viewOfListButton;

    //Группа кнопок перехода на следующую страницу
    @FindBy(xpath = "//div[@data-qa = 'pager-block']")
    public WebElement groupSelectPageButtons;

    //Кнопка перехода по страницам вакансий "дальше"
    @FindBy(xpath = "//a[text() = 'дальше']")
    public WebElement nextButton;

    //========================Методы работы с элементами страницы=======================

    //Получить данные по всем вакансиям
    public boolean getVacanciesOnPage(List<Vacancy> vacancy) {
        (new WebDriverWait(driver,10))
                //.until(ExpectedConditions.visibilityOf(viewOfListButton));// не для режима --headless
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text() = 'Списком']")));
        List<WebElement> listOfVacancies = groupElementOfVacancies
                                          .findElements(By.xpath("//div[@data-qa = 'vacancy-serp__vacancy']"));
        System.out.println("Количество вакансий на странице: " + listOfVacancies.size());
        for (int i=0; i < listOfVacancies.size(); i++) {
            String[] arrayValuesParams = getVacancyParams(listOfVacancies, i);
            vacancy.add(new Vacancy(arrayValuesParams));
        }
        //Если кнопка "далее" присутствует, то переход на новую страницу
        if (isElementPresent(By.xpath("//a[text() = 'дальше']"))) {
            nextButton.click();
            return true;
        }
        //Все страницы обработаны
        isPresentElement(listOfVacancies, 0);
        return false;
    }

    //Получить данные по вакансии
    public String[] getVacancyParams(List<WebElement> listOfVacancies, int i) {
        String[] arrayValuesParams = {"","","","","","",""};
        System.out.println("Вакансия " + i);
        String nameOfVacancy = listOfVacancies.get(i)
                .findElement(By.xpath(".//a[@data-qa = 'vacancy-serp__vacancy-title']"))
                .getText();
        System.out.println("Название вакансии: " + nameOfVacancy);
        arrayValuesParams[0] = nameOfVacancy;

        //С проверкой на наличие элемента
        String sizeOfSalary = "";
        //if (isExist(listOfVacancies.get(i),By.xpath(".//span[@data-qa = 'vacancy-serp__vacancy-compensation']"))){
        if (isPresentElement(listOfVacancies,i)){
                   sizeOfSalary = listOfVacancies.get(i)
                   .findElement(By.xpath(".//span[@data-qa = 'vacancy-serp__vacancy-compensation']"))
                   .getText();
        }
        System.out.println("Размер зарплаты: " + sizeOfSalary);
        arrayValuesParams[1] = sizeOfSalary;

        String nameOfOrganization = listOfVacancies.get(i)
                .findElement(By.xpath(".//a[@data-qa = 'vacancy-serp__vacancy-employer']"))
                .getText();
        System.out.println("Организация: " + nameOfOrganization);
        arrayValuesParams[2] = nameOfOrganization;

        String locationCity = listOfVacancies.get(i)
                .findElement(By.xpath(".//span[@data-qa = 'vacancy-serp__vacancy-address']"))
                .getText();
        System.out.println("Адрес: город - " + locationCity);
        arrayValuesParams[3] = locationCity;

        String responsibility = listOfVacancies.get(i)
                .findElement(By.xpath(".//div[@data-qa = 'vacancy-serp__vacancy_snippet_responsibility']"))
                .getText();
        System.out.println("Обязанности: " + responsibility);
        arrayValuesParams[4] = responsibility;

        String requirement = listOfVacancies.get(i)
                .findElement(By.xpath(".//div[@data-qa = 'vacancy-serp__vacancy_snippet_requirement']"))
                .getText();
        System.out.println("Требования: " + requirement);
        arrayValuesParams[5] = requirement;

        String dateOfVacancy = listOfVacancies.get(i)
                .findElement(By.xpath(".//span[@data-qa = 'vacancy-serp__vacancy-date']"))
                .getText();
        System.out.println("Дата вакансии: " + dateOfVacancy);
        arrayValuesParams[6] = dateOfVacancy;

        return arrayValuesParams;
    }

    //Проверка наличия элемента-параметра (вариант 1)
    public boolean isExist (WebElement element, By by) {
        List<WebElement> listElements = element.findElements(by);//задержа на explicit wait
        if (listElements.size() > 0){
              return true;
        }
        else {
              return false;
        }
    }

    //Проверка наличия элемента-параметра (вариант 2)
    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    //Проверка наличия элемента-параметра (вариант 3)
    public boolean isPresentElement(List<WebElement> listOfVacancies, int i) {
        String htmlString = listOfVacancies.get(i).getAttribute("innerHTML");
        return htmlString.contains("vacancy-serp__vacancy-compensation");
    }
}
