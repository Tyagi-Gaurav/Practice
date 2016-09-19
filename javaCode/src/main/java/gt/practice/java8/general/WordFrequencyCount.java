package gt.practice.java8.general;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFrequencyCount {

    public static void main(String[] args) {
        Stream<String> stringStream = Arrays.asList(data)
                .stream()
                .flatMap(str -> Arrays.asList(str.split(" ")).stream());
        Map<String, Integer> collect = stringStream.flatMap(str -> Stream.of(new keyValue(str, 1)))
                .collect(
                        Collectors.groupingBy(
                                keyValue::getKey,
                                Collectors.reducing(
                                        0,
                                        keyValue::getValue,
                                        Integer::sum)));

        System.out.println(collect);
    }

    static String[] data = {
            "As much as we try to keep Vagrant stable and bug free",
            "it is inevitable that issues will arise and Vagrant ",
            "will behave in unexpected ways. In these cases, Vagrant",
            "has amazing support channels available to assist you.",
            "When using these support channels, it is generally helpful",
            "to include debugging logs along with any error reports. ",
            "These logs can often help you troubleshoot any problems ",
            "you may be having"
    };

    static class keyValue {
        String key;
        int value;

        public keyValue(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }
    }
}
