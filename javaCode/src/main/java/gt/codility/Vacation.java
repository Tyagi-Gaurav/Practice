package gt.codility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Vacation {
    public static void main(String[] args) {
        System.out.println(findWeeks(2014, "April", "May", "Wednesday"));
        System.out.println(findWeeks(2015, "February", "May", "Thursday"));
        System.out.println(findWeeks(2016, "February", "May", "Friday"));
    }

    private static int findWeeks(int Y, String A, String B, String W) {
        int startMonth = Month.valueOf(A.toUpperCase()).getValue();
        LocalDate vacationStartDate = LocalDate.of(Y, startMonth, 1);
        int endMonth = Month.valueOf(B.toUpperCase()).getValue();
        LocalDate vacationEndDate = LocalDate.of(Y, endMonth, Month.of(endMonth).maxLength());

        LocalDate firstMonday = vacationStartDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        LocalDate lastSunday = vacationEndDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));

        return (int) ChronoUnit.WEEKS.between(firstMonday, lastSunday) + 1;
    }
}
