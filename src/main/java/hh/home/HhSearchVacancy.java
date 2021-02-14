package hh.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HhSearchVacancy {

       public static void main(String[] args) {
              //long tmStart = System.currentTimeMillis();
              HhSearchVacancy searchVacancy = new HhSearchVacancy();
              List<Vacancy> vacancy = new ArrayList<Vacancy>();
              //Запуск Webdriver и стартовой страницы
              WebDriver driver = searchVacancy.setupAndStartWebDriver();
              //Создание необходимых страниц
              StartPage startPage = new StartPage(driver);
              RegionsPage regionsPage = new RegionsPage(driver);
              VacanciesPage vacanciesPage = new VacanciesPage(driver);
              //Поиск вакансий в Москве
              if (!startPage.getRegionCityName().equals("Москва")) {
                      startPage.clickSelectRegionButton();
                      if (regionsPage.setCityName("Москва")) {
                        startPage.inputTextInSearchString("Java Selenium");
                        startPage.clickSearchButton();
                        while (vacanciesPage.getVacanciesOnPage(vacancy));
                        System.out.println(vacancy.size());
                        //long tmStop = System.currentTimeMillis();
                        //System.out.println("Длительность: " + (tmStop-tmStart));
                      } else {System.out.println("Заданного города нет в списке доступных");}
               }
       }

       public WebDriver setupAndStartWebDriver() {
              //определение драйвера браузера (path берем из файла конфигурации)
              System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
              //Разные варианты запуска браузера
              ChromeOptions options = new ChromeOptions();
              //options.addArguments("--no-startup-window");
              //options.addArguments("no-startup-window");
              //options.addArguments("--headless");
              //options.addArguments("--incognito");
              options.addArguments("--start-maximized");
              DesiredCapabilities capabilities = new DesiredCapabilities();
              capabilities.setCapability(ChromeOptions.CAPABILITY, options);
              options.merge(capabilities);
              //создание экземпляра драйвера (открывается браузер)
              WebDriver driver = new ChromeDriver(options);
              //неявное ожидание = 15 сек при загрузке страницы
              driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
              //неявное ожидание = 1 сек при каждом поиске элемента
              driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
              //Загрузка стартовой страницы
              driver.get(ConfProperties.getProperty("startpage"));
              return driver;
       }

       public void mySleep(int delayTimeInmSec) {
              long startTime = System.currentTimeMillis();
              long currentTime = startTime;
              while (currentTime - startTime < delayTimeInmSec) {
                     currentTime = System.currentTimeMillis();
              }

       }
}
