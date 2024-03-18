package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    private DataHelper dataHelper = new DataHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        // Configuration.headless = true;
        open("http://localhost:6009");
    }

    @AfterEach
    void clean() {
        DBHelper.clear();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        // DBHelper.clear();
    }

    @Test
    void shouldSuccessCreditPay() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startCreditPay();
        paymentPage.fillCardData(dataHelper.createApprovedCard());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastCreditPaymentStatus();
        assertEquals("APPROVED", lastPaymentStatus);
    }
}
