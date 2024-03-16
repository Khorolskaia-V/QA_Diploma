package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

public class MainPage {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement buyByCreditButton = $(byText("Купить в кредит"));

    public PaymentPage deafultPay() {
        buyButton.click();
        return new PaymentPage();
    }

    public CreditPaymentPage creditPay() {
        buyByCreditButton.click();
        return new CreditPaymentPage();
    }
}