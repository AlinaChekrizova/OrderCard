package ru.netology;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderCardTest {
    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSendForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");

    }

    @Test
    void shouldCheckNameValidation() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivan 12345");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");

    }
    @Test
    void shouldCheckEmptyNameValidation() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");

    }

    @Test
    void shouldCheckPhoneValidation() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("djfgljsdgl");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> inputSub = driver.findElements(By.cssSelector(".input__sub"));
        String actual = inputSub.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");

    }
    @Test
    void shouldCheckEmptyPhoneValidation() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мария Сидорова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        List<WebElement> inputSub = driver.findElements(By.cssSelector(".input__sub"));
        String actual = inputSub.get(1).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual, "Текст сообщения не совпадает!");

    }
    @Test
    void shouldCheckNoCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мария Сидорова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991112233");
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector(".input_invalid")).getCssValue("color");
        String expected = "rgba(255, 92, 92, 1)";
        assertEquals(expected, actual, "Цвет текста сообщения не совпадает!");

    }

}
