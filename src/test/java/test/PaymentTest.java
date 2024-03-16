package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;


class PaymentTest {

    private DataHelper dataHelper = new DataHelper();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        // Configuration.headless = true;
        //DBHelper.clear();
        open("http://localhost:6009");
    }

    @AfterEach
    void clean() {
        // DBHelper.clear();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        // DBHelper.clear();
    }

    @Test
    void shouldSuccessPayWithApprovedCard() {
        MainPage mainPage = new MainPage();
        PaymentPage paymentPage = mainPage.deafultPay();
        paymentPage.fillCardData(dataHelper.createFirstCard());
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals("APPROVED", lastPaymentStatus);
    }
}