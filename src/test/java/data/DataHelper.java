package data;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataHelper {
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

    public Card createFirstCard() {
        return new Card(
                "4444 4444 4444 4441",
                DataHelper.getNextAfterCurrentMonth(),
                DataHelper.getNextYearAfterCurrent(),
                "IVAN IVANOV",
                "123"
        );
    }

    private static String getNextAfterCurrentMonth() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getNextYearAfterCurrent() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
}