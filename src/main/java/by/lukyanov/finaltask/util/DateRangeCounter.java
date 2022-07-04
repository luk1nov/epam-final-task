package by.lukyanov.finaltask.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRangeCounter {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final int BEGIN_DATE_INDEX = 0;
    private static final int END_DATE_INDEX = 1;
    private LocalDate beginDate;
    private LocalDate endDate;

    public DateRangeCounter(String dateRange) {
        init(dateRange);
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int countDays(){
        return Math.toIntExact(ChronoUnit.DAYS.between(beginDate, endDate)) + 1;
    }

    private void init(String dateRange){
        List<LocalDate> foundDateList = new ArrayList<>();
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(dateRange);
        while (matcher.find()){
            LocalDate foundDate = LocalDate.parse(matcher.group());
            foundDateList.add(foundDate);
        }
        this.beginDate = foundDateList.get(BEGIN_DATE_INDEX);
        this.endDate = foundDateList.size() > 1 ? foundDateList.get(END_DATE_INDEX) : foundDateList.get(BEGIN_DATE_INDEX);
    }
}
