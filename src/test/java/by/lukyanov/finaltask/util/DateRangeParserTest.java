package by.lukyanov.finaltask.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeParserTest {
    private static final String FIRST_DATE = "2022-03-20";
    private static final String SECOND_DATE = "2022-03-25";
    private static final String DATE_RANGE = "2022-03-20 to 2022-03-25";
    private static final LocalDate firstDate = LocalDate.parse(FIRST_DATE);
    private static final LocalDate secondDate = LocalDate.parse(SECOND_DATE);

    @Test
    void countDays() {
        int expectedDays = 6;
        assertEquals(expectedDays, DateRangeParser.countDays(firstDate, secondDate));
    }

    @Test
    void parse() {
        List<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(LocalDate.parse(FIRST_DATE));
        localDateList.add(LocalDate.parse(SECOND_DATE));
        assertEquals(localDateList, DateRangeParser.parse(DATE_RANGE));
    }
}