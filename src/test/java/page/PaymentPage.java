package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement buyByCreditButton = $(byText("Купить в кредит"));
    private SelenideElement cardNumberInput = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInput = $("[placeholder='08']");
    private SelenideElement yearInput = $("[placeholder='22']");
    private SelenideElement ownerInput = $$("[class='input__control']").get(3);
    private SelenideElement cvcInput = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(byText("Операция одобрена Банком."));
    private SelenideElement errorMessage = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement emptyInputMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement invalidFormatMessage = $(byText("Неверный формат"));
    private SelenideElement invalidDateMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement expiredCardMessage = $(byText("Истёк срок действия карты"));

    public void clearForm() {
        cardNumberInput.doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        monthInput.doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        yearInput.doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        ownerInput.doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        cvcInput.doubleClick().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public void startPay() {
        buyButton.click();
    }

    public void startCreditPay() {
        buyByCreditButton.click();
    }

    public void fillCardData(DataHelper.Card card) {
        cardNumberInput.setValue(card.getNumber());
        monthInput.setValue(card.getMonth());
        yearInput.setValue(card.getYear());
        ownerInput.setValue(card.getOwner());
        cvcInput.setValue(card.getCvc());
    }

    public void submit() {
        continueButton.click();
    }

    public void checkSuccessMessage() {
        successMessage.should(visible, Duration.ofSeconds(15));
    }

    public void checkErrorMessage() {
        errorMessage.should(visible, Duration.ofSeconds(15));
    }

    public void checkInvalidFormatMessage() {
        invalidFormatMessage.shouldBe(visible);
    }

    public Integer getNumberValueLength() {
        String value = cardNumberInput.getValue();
        if (value == null) return 0;
        return value.length();
    }

    public void checkInvalidDateMessage() {
        invalidDateMessage.shouldBe(visible);
    }

    public void checkExpiredCardMessage() {
        expiredCardMessage.shouldBe(visible);
    }

    public void checkRequiredFieldMessage() { emptyInputMessage.shouldBe(visible); }

}
