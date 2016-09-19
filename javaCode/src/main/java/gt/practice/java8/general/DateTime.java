package gt.practice.java8.general;

import java.time.Instant;
import java.time.ZoneId;

public class DateTime {
    public static void main(String[] args) {
        System.out.println(Instant.now());
        System.out.println(Instant.now().getEpochSecond());
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("Current Time Millis: " + currentTimeMillis);
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(System.nanoTime());
        System.out.println(Instant.ofEpochMilli(currentTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
