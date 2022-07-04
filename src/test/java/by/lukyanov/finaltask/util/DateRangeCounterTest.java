package by.lukyanov.finaltask.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeCounterTest {
    private static final String DATE = "2022-03-20";
    private static final String MULTIPLE_DATE_RANGE = "2022-03-20:2022-03-25";
    private static final LocalDate FIRST_DATE = LocalDate.parse(DATE);

    @Test
    void countMultipleDays() {
        DateRangeCounter counter = new DateRangeCounter(MULTIPLE_DATE_RANGE);
        int expectedDays = 6;
        assertEquals(expectedDays, counter.countDays());
    }

    @Test
    void countOneDay() {
        DateRangeCounter counter = new DateRangeCounter(DATE);
        int expectedDays = 1;
        assertEquals(expectedDays, counter.countDays());
    }

    @Test
    void getBeginDateTest() {
        DateRangeCounter counter = new DateRangeCounter(DATE);
        assertEquals(FIRST_DATE, counter.getBeginDate());
    }

    @Test
    void getEndDateTest() {
        DateRangeCounter counter = new DateRangeCounter(DATE);
        assertEquals(FIRST_DATE, counter.getEndDate());
    }
}