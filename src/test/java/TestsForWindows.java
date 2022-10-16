import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Set;

public class TestsForWindows {

    private final org.apache.logging.log4j.Logger logger= LogManager.getLogger(TestsForWindows.class);
    private ChromeOptions options;
    private WebDriver driver;

    String headlessText = "Онлайн‑курсы для профессионалов, дистанционное обучение";

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();

    }

    @AfterEach
    public void startDown(){
        if(driver != null){driver.quit();}
    }

    /**
     * Тест падает в ошибку, так как сторка, выдаваемая поисковиком длиннее. Нужно подправить ожидаемый текст или так и задуманно?
     */
    @Test
    public void openChromeInHeadlees(){
        options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.get("https://duckduckgo.com/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ОТУС");
        driver.findElement(By.id("search_button_homepage")).submit();
        Assertions.assertEquals(headlessText,driver.findElement(By.cssSelector("#r1-0 h2 span")).getText());
        System.out.println(driver.findElement(By.cssSelector("#r1-0 h2 span")).getText());
    }

    @Test
    public void openChromeInKiosk(){
        options = new ChromeOptions();
        options.addArguments("kiosk");
        driver = new ChromeDriver(options);
        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        driver.findElement(By.cssSelector(".content-overlay")).click();
        boolean elem = driver.findElement(By.className("pp_pic_holder")).isDisplayed();
        Assertions.assertTrue(elem);
        System.out.println("Done");
    }

    @Test
    public void openChromeInFullScreen(){
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        driver.get("https://otus.ru");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        auth();
    }
    private void auth(){
        driver.findElement(By.cssSelector(".header2__auth")).click();
//        driver.findElement(By.cssSelector("[name= 'email'")).sendKeys(System.getProperty("email"));
//        driver.findElement(By.cssSelector(".js-psw-input")).sendKeys(System.getProperty("pass"));
//        driver.findElement(By.cssSelector("[type = submit]")).submit();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        /**
         * не смог подобрать CSS
         */
        driver.findElement(By.xpath("//form[@action = '/login/']//input[@name = 'email']")).sendKeys(System.getProperty("email"));
        driver.findElement(By.xpath("//form[@action = '/login/']//input[@name = 'password']")).sendKeys(System.getProperty("pass"));
        driver.findElement(By.xpath("//form[@action = '/login/']//button[@type = 'submit']")).submit();
        Set<Cookie> cookies =driver.manage().getCookies();
        logger.info(cookies.toString());
        Assertions.assertNotNull(cookies);
        Assertions.assertNotEquals(0,cookies.size());

    }

}
