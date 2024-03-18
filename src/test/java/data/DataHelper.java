package data;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DataHelper {

    public static String APPROVED = "APPROVED";

    public static String DECLINED = "DECLINED";
    private static final Faker faker = new Faker(Locale.ENGLISH);
    public class Card {

        public Card(
                String number,
                String month,
                String year,
                String owner,
                String cvc
        ) {
            this.number = number;
            this.month = month;
            this.year = year;
            this.owner = owner;
            this.cvc = cvc;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Getter
        private String number;
        private String month;

        private String year;
        private String owner;
        private String cvc;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getCvc() {
            return cvc;
        }

        public void setCvc(String cvc) {
            this.cvc = cvc;
        }
    }

    public Card createApprovedCard() {
        return new Card(
                "4444 4444 4444 4441",
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                "IVAN IVANOV",
                "123"
        );
    }

    public Card createDeclinedCard() {
        return new Card(
                "4444 4444 4444 4442",
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                "IVAN IVANOV",
                "123"
        );
    }

    public Card createApprovedCardWithDoubleFirstNamedOwner() {
        Card card = createApprovedCard();
        card.setOwner(generateDoubleFirstNameOwner());
        return card;
    }

    public Card createDeclinedCardWithDoubleFirstNamedOwner() {
        Card card = createDeclinedCard();
        card.setOwner(generateDoubleFirstNameOwner());
        return card;
    }

    public Card createApprovedCardWithDoubleLastNamedOwner() {
        Card card = createApprovedCard();
        card.setOwner(generateDoubleLastNameOwner());
        return card;
    }

    public Card createDeclinedCardWithDoubleLastNamedOwner() {
        Card card = createDeclinedCard();
        card.setOwner(generateDoubleLastNameOwner());
        return card;
    }

    public Card createEmptyNumberCard() {
        Card card = createApprovedCard();
        card.setNumber("");
        return card;
    }

    public Card createInvalidNumberCard() {
        Card card = createApprovedCard();
        card.setNumber(faker.letterify("???? ???? ???? ????"));
        return card;
    }

    public Card createShorterValueNumberCard() {
        Card card = createApprovedCard();
        card.setNumber(faker.numerify("#### #### #### ###"));
        return card;
    }

    public Card createLongerValueNumberCard() {
        Card card = createApprovedCard();
        card.setNumber(faker.numerify("#### #### #### #####"));
        return card;
    }

    public Card createOutOfRangeMonthCard() {
        Card card = createApprovedCard();
        String invalidMonth = String.valueOf(faker.number().numberBetween(13, 99));
        card.setMonth(invalidMonth);
        return card;
    }

    public Card createInvalidMonthCard() {
        Card card = createApprovedCard();
        String invalidMonth = String.valueOf(faker.number().numberBetween(1, 9));
        card.setMonth(invalidMonth);
        return card;
    }

    public Card createExpiredCard() {
        Card card = createApprovedCard();
        card.setMonth(getPreviousMonth());
        card.setYear(getCurrentYear());
        return card;
    }

    public Card createEmptyMonthCard() {
        Card card = createApprovedCard();
        card.setMonth("");
        return card;
    }

    public Card createLetterMonthCard() {
        Card card = createApprovedCard();
        card.setMonth(generateInvalidMonth());
        return card;
    }

    public Card createZeroMonthApprovedCard() {
        Card card = createApprovedCard();
        card.setMonth("00");
        return card;
    }

    public Card createZeroMonthDeclinedCard() {
        Card card = createDeclinedCard();
        card.setMonth("00");
        return card;
    }

    public Card createPrviousYearApprovedCard() {
        Card card = createApprovedCard();
        card.setYear(getPrevousYear());
        return card;
    }

    public Card createPrviousYearDeclinedCard() {
        Card card = createDeclinedCard();
        card.setYear(getPrevousYear());
        return card;
    }

    public Card createPlusSixYearApprovedCard() {
        Card card = createApprovedCard();
        card.setYear(getPlusSixYears());
        return card;
    }

    public Card createPlusSixYearDeclinedCard() {
        Card card = createDeclinedCard();
        card.setYear(getPlusSixYears());
        return card;
    }

    public Card createEmptyYearCard() {
        Card card = createApprovedCard();
        card.setYear("");
        return card;
    }

    public Card createOneSymbolYearAcceptedCard() {
        Card card = createApprovedCard();
        card.setYear(String.valueOf(faker.numerify("#")));
        return card;
    }

    public Card createOneSymbolYearDeclinedCard() {
        Card card = createDeclinedCard();
        card.setYear(String.valueOf(faker.numerify("#")));
        return card;
    }

    public Card createInvalidYearCard() {
        Card card = createDeclinedCard();
        card.setYear(String.valueOf(faker.letterify("?")));
        return card;
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

    private static String generateInvalidMonth() {
        return faker.letterify("??");
    }
}