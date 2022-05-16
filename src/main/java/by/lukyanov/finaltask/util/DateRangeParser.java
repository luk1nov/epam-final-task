package by.lukyanov.finaltask.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRangeParser {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    public static final int BEGIN_DATE_INDEX = 0;
    public static final int END_DATE_INDEX = 1;

    public static List<LocalDate> parse(String dateRange){
        List<LocalDate> foundDateList = new ArrayList<>();
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(dateRange);
        while (matcher.find()){
            LocalDate foundDate = LocalDate.parse(matcher.group());
            foundDateList.add(foundDate);
        }
        return foundDateList;
    }

    public static int countDays(LocalDate beginDate, LocalDate endDate){
        return Math.toIntExact(ChronoUnit.DAYS.between(beginDate, endDate)) + 1;
    }
}
