package data;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataHelper {

    private static String APPROVED = "APPROVED";

    private static String DECLINED = "DECLINED";
    private static final Faker faker = new Faker(Locale.ENGLISH);
    private static final Faker cyrillicFaker = new Faker(new Locale("ru", "RU"));

    @Value
    public class Card {
        String number;
        String month;
        String year;
        String owner;
        String cvc;
    }

    public Card createApprovedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createDeclinedCard() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public Card createApprovedCardWithDoubleFirstNamedOwner() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateDoubleFirstNameOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createDeclinedCardWithDoubleFirstNamedOwner() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateDoubleFirstNameOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createApprovedCardWithDoubleLastNamedOwner() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateDoubleLastNameOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createDeclinedCardWithDoubleLastNamedOwner() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateDoubleLastNameOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithDoubleFirstAndLastNameOwner() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateDoubleFirstAndLastNameOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithOneSymbolsInName() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                faker.letterify("?") + " " + faker.letterify("?"),
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWith36SymbolsInName() {
        String lastName = faker.letterify("?????????????????????????????????????");
        String firstName = faker.letterify("?????????????????????????????????????");
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                lastName + " " + firstName,
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithCyrillicSymbolsName() {
        String lastName = cyrillicFaker.name().lastName().toUpperCase();
        String firstName = cyrillicFaker.name().firstName().toUpperCase();
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                lastName + " " + firstName,
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithNumberSymbolsName() {
        String lastName = faker.numerify("#####");
        String firstName = faker.numerify("########");
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                lastName + " " + firstName,
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithoutWhitespace() {
        String lastName = faker.name().lastName().toUpperCase();
        String firstName = faker.name().firstName().toUpperCase();
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                lastName + firstName,
                DataHelper.generateValidCvc()
        );
    }

    public Card createAcceptedCardWithEmptyName() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                "",
                DataHelper.generateValidCvc()
        );
    }

    public Card createEmptyNumberCard() {
        return new Card(
                "",
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createInvalidNumberCard() {
        return new Card(
                faker.letterify("???? ???? ???? ????"),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createShorterValueNumberCard() {
        return new Card(
                faker.numerify("#### #### #### ###"),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createLongerValueNumberCard() {
        return new Card(
                faker.numerify("#### #### #### #####"),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createOutOfRangeMonthCard() {
        String invalidMonth = String.valueOf(faker.number().numberBetween(13, 99));
        return new Card(
                DataHelper.getApprovedCardNumber(),
                invalidMonth,
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createInvalidMonthCard() {
        String invalidMonth = String.valueOf(faker.number().numberBetween(1, 9));
        return new Card(
                DataHelper.getApprovedCardNumber(),
                invalidMonth,
                DataHelper.getNextYearAfterCurrent(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createExpiredCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getPreviousMonth(),
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createEmptyMonthCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                "",
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createLetterMonthCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.generateInvalidMonth(),
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createZeroMonthApprovedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                "00",
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createZeroMonthDeclinedCard() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                "00",
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createPreviousYearApprovedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getPrevousYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createPreviousYearDeclinedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getPrevousYear(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createPlusSixYearApprovedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getPlusSixYears(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createPlusSixYearDeclinedCard() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getPlusSixYears(),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createEmptyYearCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                "",
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createOneSymbolYearAcceptedCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                String.valueOf(faker.numerify("#")),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createOneSymbolYearDeclinedCard() {
        return new Card(
                DataHelper.getDeclinedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                String.valueOf(faker.numerify("#")),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createInvalidYearCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                String.valueOf(faker.letterify("?")),
                DataHelper.generateValidOwner(),
                DataHelper.generateValidCvc()
        );
    }

    public Card createEmptyCvcCard() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                ""
        );
    }

    public Card createOneNumberCvc() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                faker.numerify("?")
        );
    }

    public Card createCvcWIthLetters() {
        return new Card(
                DataHelper.getApprovedCardNumber(),
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getCurrentYear(),
                DataHelper.generateValidOwner(),
                faker.letterify("###")
        );
    }

    public static Boolean isPaymentStatusApproved(String paymentStatus) {
        return paymentStatus.equals(DataHelper.APPROVED);
    }

    public static Boolean isPaymentStatusDeclined(String paymentStatus) {
        return paymentStatus.equals(DataHelper.DECLINED);
    }

    private static String getNextAfterCurrentMonth() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getNextYearAfterCurrent() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPlusSixYears() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPrevousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateDoubleFirstNameOwner() {
        String firstName = faker.name().firstName().toUpperCase() + "-" + faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        return firstName + " " + lastName;
    }

    private static String generateDoubleLastNameOwner() {
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase() + "-" + faker.name().lastName().toUpperCase();
        return firstName + " " + lastName;
    }

    private static String generateDoubleFirstAndLastNameOwner() {
        String firstName = faker.name().firstName().toUpperCase() + "-" + faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase() + "-" + faker.name().lastName().toUpperCase();
        return firstName + " " + lastName;
    }

    private static String generateValidOwner() {
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();

        return lastName + " " + firstName;
    }

    private static String generateValidCvc() {
        return faker.numerify("###");
    }

    private static String generateInvalidMonth() {
        return faker.letterify("??");
    }
}