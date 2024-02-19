package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    private String dateGeneration(int addedDays) {
        LocalDate date = LocalDate.now();
        LocalDate newDate = date.plusDays(addedDays);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateAdd = newDate.format(dateTimeFormatter);
        return dateAdd;
    }

    @Test
    void shouldRegisterFormTest() {
        $("[data-test-id='city'] input").setValue("Ярославль");
        $("[data-test-id='date'] input").doubleClick().sendKeys(dateGeneration(5));
        $("[data-test-id='name'] input").setValue("Горбунов Иван");
        $("[data-test-id='phone'] input").setValue("+79109691089");
        $("[data-test-id='agreement'] span.checkbox__box").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + dateGeneration(5)));
    }
}
