package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBHelper;
import data.DataHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.APPROVED;
import static data.DataHelper.DECLINED;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Покупка по дебетовой карте")
class DebitTest {

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

    @Story("Успешная оплата по действующей карте")
    @Test
    void shouldSuccessPayWithApprovedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createApprovedCard());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(APPROVED, lastPaymentStatus);
    }

    @Story("Отклонение оплаты")
    @Test
    void shouldErrorPayWithDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createDeclinedCard());
        paymentPage.submit();
        paymentPage.checkErrorMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(DECLINED, lastPaymentStatus);
        // баг
    }

    @Story("Успешная оплата. Имя с дефисом")
    @Test
    void shouldSuccessPayDoubleNamedOwner() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createApprovedCardWithDoubleFirstNamedOwner());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(APPROVED, lastPaymentStatus);
    }

    @Story("Отклонение оплаты. Имя с дефисом")
    @Test
    void shouldErrorPayDoubleNamedOwner() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createDeclinedCardWithDoubleFirstNamedOwner());
        paymentPage.submit();
        paymentPage.checkErrorMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(DECLINED, lastPaymentStatus);
    }

    @Story("Успешная оплата. Фамилия с дефисом")
    @Test
    void shouldSuccessPayDoubleLastNamedOwner() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createApprovedCardWithDoubleLastNamedOwner());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(APPROVED, lastPaymentStatus);
    }

    @Story("Отклонение оплаты. Фамилия с дефисом")
    @Test
    void shouldErrorPayDoubleLastNamedOwner() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createDeclinedCardWithDoubleLastNamedOwner());
        paymentPage.submit();
        paymentPage.checkErrorMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertEquals(DECLINED, lastPaymentStatus);
    }

    @Story("Проверка обязательности поля номер карты")
    @Test
    void shouldValidateCardNumber() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createEmptyNumberCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Блокирование ввода латиницы в поле номер карты")
    @Test
    void shouldBlockEnterInvalidSymbols() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createInvalidNumberCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Валидация неполного ввода номера карты")
    @Test
    void shouldValidateShorterInputValue() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createShorterValueNumberCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Валидация лишнего ввода номера карты")
    @Test
    void shouldValidateLongerInputValue() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createLongerValueNumberCard());
        Integer actualNumberValueLength = paymentPage.getNumberValueLength();
        assertEquals(19, actualNumberValueLength);
    }

    @Story("Валидация месяца > 12")
    @Test
    void shouldValidateOutOfRangeMonth() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createOutOfRangeMonthCard());
        paymentPage.submit();
        paymentPage.checkInvalidDateMessage();
    }

    @Story("Валидация месяца при вводе одним символом")
    @Test
    void shouldValidateInvalidMonthFormat() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createInvalidMonthCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Ошибка оплаты при вводе просроченной карты")
    @Test
    void shouldValidateExpiredCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createExpiredCard());
        paymentPage.submit();
        paymentPage.checkInvalidDateMessage();
    }

    @Story("Ошибка оплаты с пустым месяцем")
    @Test
    void shouldValidateEmptyMonth() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createEmptyMonthCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Блокирование ввода латиницы в поле месяц")
    @Test
    void shouldValidateInvalidMonth() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createLetterMonthCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Неверный формат при вводе в месяц 00. Approved")
    @Test
    void shouldValidateZeroMonthApprovedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createZeroMonthApprovedCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
        // баг
    }

    @Story("Неверный формат при вводе в месяц 00. DECLINED")
    @Test
    void shouldValidateZeroMonthDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createZeroMonthDeclinedCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
        // баг
    }

    @Story("Ошибка при оплате по просроченной карте (год). Accepted")
    @Test
    void shouldAcceptedCardErrorWhenPreviousYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPrviousYearApprovedCard());
        paymentPage.submit();
        paymentPage.checkExpiredCardMessage();
    }

    @Story("Ошибка при оплате по просроченной карте (год). Declined")
    @Test
    void shouldDeclinedCardErrorWhenPreviousYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPrviousYearDeclinedCard());
        paymentPage.submit();
        paymentPage.checkExpiredCardMessage();
    }

    @Story("Ошибка при оплате карты +6 лет. Accepted")
    @Test
    void shouldAcceptedCardErrorWhenPlusSixYears() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPlusSixYearApprovedCard());
        paymentPage.submit();
        paymentPage.checkInvalidDateMessage();
    }

    @Story("Ошибка при оплате карты +6 лет. Declined")
    @Test
    void shouldDeclinedCardErrorWhenPlusSixYears() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPlusSixYearDeclinedCard());
        paymentPage.submit();
        paymentPage.checkInvalidDateMessage();
    }

    @Story("Валидация неполного ввода года. Accepted")
    @Test
    void shouldValidateOneSymbolYearAcceptedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createOneSymbolYearAcceptedCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Валидация неполного ввода года. Accepted")
    @Test
    void shouldValidateOneSymbolYearDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createOneSymbolYearDeclinedCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

    @Story("Валидация ввода года латиницей")
    @Test
    void shouldValidateInvalidYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createInvalidYearCard());
        paymentPage.submit();
        paymentPage.checkIvalidFormatMessage();
    }

}
