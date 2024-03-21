package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DBHelper;
import data.DataHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
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
    }

    @Story("Успешная покупка тура по карте со статусом APPROVED")
    @Test
    void shouldSuccessPayWithApprovedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createApprovedCard());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertTrue(DataHelper.isPaymentStatusApproved(lastPaymentStatus));
    }

    @Story("Отклонение покупки тура по карте со статусом с DECLINED")
    @Test
    void shouldErrorPayWithDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createDeclinedCard());
        paymentPage.submit();
        paymentPage.checkErrorMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertTrue(DataHelper.isPaymentStatusDeclined(lastPaymentStatus));
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
        assertTrue(DataHelper.isPaymentStatusApproved(lastPaymentStatus));
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
        assertTrue(DataHelper.isPaymentStatusDeclined(lastPaymentStatus));
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
        assertTrue(DataHelper.isPaymentStatusApproved(lastPaymentStatus));
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
        assertTrue(DataHelper.isPaymentStatusDeclined(lastPaymentStatus));
    }

    @Story("Успешная оплата. Фамилия и имя с дефисом")
    @Test
    void shouldSuccessPayDoubleLastLastAndFirstNameOwner() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithDoubleFirstAndLastNameOwner());
        paymentPage.submit();
        paymentPage.checkSuccessMessage();
        String lastPaymentStatus = DBHelper.getLastPaymentStatus();
        assertTrue(DataHelper.isPaymentStatusApproved(lastPaymentStatus));
    }

    @Story("Проверка обязательности заполнения поля номер карты")
    @Test
    void shouldValidateCardNumber() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createEmptyNumberCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Блокирование ввода латиницы в поле номер карты")
    @Test
    void shouldBlockEnterInvalidSymbols() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createInvalidNumberCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Валидация неполного ввода номера карты")
    @Test
    void shouldValidateShorterInputValue() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createShorterValueNumberCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Валидация ввода лишней цифры в номер карты")
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
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Ошибка оплаты  запроса с указанием числа предыдущего месяца")
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
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Блокирование ввода латиницы в поле месяц")
    @Test
    void shouldValidateInvalidMonth() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createLetterMonthCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Неверный формат при вводе в месяц 00. Approved")
    @Test
    void shouldValidateZeroMonthApprovedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createZeroMonthApprovedCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Неверный формат при вводе в месяц 00. DECLINED")
    @Test
    void shouldValidateZeroMonthDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createZeroMonthDeclinedCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Ошибка при оплате по просроченной карте (год). Accepted")
    @Test
    void shouldAcceptedCardErrorWhenPreviousYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPreviousYearDeclinedCard());
        paymentPage.submit();
        paymentPage.checkExpiredCardMessage();
    }

    @Story("Ошибка при оплате по просроченной карте (год). Declined")
    @Test
    void shouldDeclinedCardErrorWhenPreviousYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createPreviousYearDeclinedCard());
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
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Валидация неполного ввода года. Accepted")
    @Test
    void shouldValidateOneSymbolYearDeclinedCard() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createOneSymbolYearDeclinedCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Валидация ввода года латиницей")
    @Test
    void shouldValidateInvalidYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createInvalidYearCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Валидация пустого года")
    @Test
    void shouldValidateEmptyYear() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createEmptyYearCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("Ввод фамилии и имени по одной букве")
    @Test
    void shouldErrorWhenNameAndLastNameByOneLetter() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithOneSymbolsInName());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Ввод фамилии и имени по 35 символов")
    @Test
    void shouldErrorWhenNameAndLastNameBy35Letters() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWith36SymbolsInName());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Ввод фамилии и имени кириллицей")
    @Test
    void shouldErrorWhenNameIsCyrillicLetters() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithCyrillicSymbolsName());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Ввод фамилии и имени цифрами")
    @Test
    void shouldErrorWhenNameIsNumbers() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithNumberSymbolsName());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Ввод фамилии и имени без пробела")
    @Test
    void shouldErrorWhenNameWithoutWhitespace() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithoutWhitespace());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        // баг
    }

    @Story("Пустое поле Владелец")
    @Test
    void shouldErrorWhenNameIsEmpty() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createAcceptedCardWithEmptyName());
        paymentPage.submit();
        paymentPage.checkRequiredFieldMessage();
    }

    @Story("Пустое поле CVC")
    @Test
    void shouldErrorWhenCvcIsEmpty() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createEmptyCvcCard());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
        //,fu надписи
    }

    @Story("CVC из одного символа")
    @Test
    void shouldErrorWhenCvcIsOneNumber() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createOneNumberCvc());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

    @Story("CVC из букв")
    @Test
    void shouldErrorWhenCvcIsLetters() {
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.startPay();
        paymentPage.fillCardData(dataHelper.createCvcWIthLetters());
        paymentPage.submit();
        paymentPage.checkInvalidFormatMessage();
    }

}
