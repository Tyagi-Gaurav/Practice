package gt.practice.jpm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Type Ahead
 * Programming challenge description:
 * Your task is to build a type-ahead feature for an upcoming product.
 * <p>
 * When the user enters a word or set of words, we want to be able to "predict" what they're going to type next
 * with some level of accuracy. We've chosen to implement this using the N-Gram algorithm defined here
 * http://en.wikipedia.org/wiki/N-gram.
 * <p>
 * Your program should return a tuple of predictions sorted high to low based on the prediction score
 * (up to a maximum of three decimal places, or pad with zeroes up to three decimal places i.e.
 * 0.2 should be shown as 0.200), (if predictions share the same score, they are sorted
 * alphabetically.)
 * <p>
 * Words should be split by whitespace with all non-alphanumeric characters stripped off the beginning and end.
 * <p>
 * Prediction scores are calculated like this:
 * Occurrences of a word after an N-gram / total number of words after an N-gram
 * e.g. for an N-gram of length 2:
 * ONE TWO ONE TWO THREE TWO THREE
 * "TWO" has the following predictions:
 * THREE:0.666,ONE:0.333
 * "THREE" occurred 2 times after a "TWO" and "ONE" occurred 1 time after a "TWO", for a total of 3 occurrences after a "TWO".
 * <p>
 * Your program will run against the following text. You may hardcode it into your program:
 * Mary had a little lamb its fleece was white as snow;
 * And everywhere that Mary went, the lamb was sure to go.
 * It followed her to school one day, which was against the rule;
 * It made the children laugh and play, to see a lamb at school.
 * And so the teacher turned it out, but still it lingered near,
 * And waited patiently about till Mary did appear.
 * "Why does the lamb love Mary so?" the eager children cry;"Why, Mary loves the lamb, you know" the teacher did reply."
 * <p>
 * Input:
 * Your program should read lines of text from standard input. Each line contains a number followed by a
 * string, separated by a comma. The number is the n-gram length. The string is the text from the user. You
 * will be predicting the text following this string.
 * <p>
 * Output:
 * For each line of input print a single line to standard output which is the predictions for what the user is
 * going to type next.
 */

public class JPMProblem {
    private static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        String input = "Mary had a little lamb its fleece was white as snow;" +
                "And everywhere that Mary went, the lamb was sure to go." +
                "It followed her to school one day, which was against the rule;" +
                "It made the children laugh and play, to see a lamb at school." +
                "And so the teacher turned it out, but still it lingered near," +
                "And waited patiently about till Mary did appear." +
                "\"Why does the lamb love Mary so?\" the eager children cry;\"Why, Mary loves the lamb, you know\" the teacher did reply.\"";

        String processedInput = input.replaceAll("[^A-Za-z0-9]", SPACE);
        String[] wordsList = processedInput.toLowerCase().split("\\s+");
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        Map<String, Double> occurrencesMap = new HashMap<>();
        while ((line = in.readLine()) != null) {
            String[] splitInput = line.split(",");
            findNGramTuples(wordsList, Integer.parseInt(splitInput[0]), splitInput[1], occurrencesMap);
            double totalTuples = occurrencesMap.values().stream().mapToDouble(value -> value).sum();

            List<Map.Entry<String, Double>> collect = occurrencesMap.entrySet()
                    .stream()
                    .peek(es -> es.setValue(es.getValue() / totalTuples))
                    .sorted((o1, o2) -> {
                        int compareResult = o1.getValue().compareTo(o2.getValue());
                        if (compareResult == 0) {
                            return o1.getKey().compareTo(o2.getKey());
                        } else {
                            return compareResult * -1;
                        }
                    })
                    .collect(Collectors.toList());

            for (int i = 0; i < collect.size(); i++) {
                Map.Entry<String, Double> entrySet = collect.get(i);
                System.out.printf("%s,%.3f", entrySet.getKey(), entrySet.getValue());
                if (i + 1 < collect.size()) {
                    System.out.print(";");
                }
            }
        }
    }

    private static void findNGramTuples(String[] wordsList, int ngramIndex, String text,
                                        Map<String, Double> occurrencesMap) {
        for (int i = 0; i + ngramIndex < wordsList.length; i += ngramIndex - 1) {
            String ngram = generateNGram(wordsList, i, ngramIndex);
            if (ngram.contains(text)) {
                String remainingString = findWordsAfterTheText(ngram, text);
                if (!remainingString.isEmpty()) {
                    if (occurrencesMap.containsKey(remainingString)) {
                        occurrencesMap.put(remainingString, occurrencesMap.get(remainingString) + 1.0);
                    } else {
                        occurrencesMap.put(remainingString, 1.0);
                    }
                }
            }
        }

    }

    private static String findWordsAfterTheText(String ngram, String text) {
        int startIndex = ngram.indexOf(text);
        return ngram.substring(startIndex + text.length()).trim();
    }

    private static String generateNGram(String[] wordsList, int i, int ngram) {
        StringBuilder builder = new StringBuilder();
        for (int j = i; j < i + ngram; ++j) {
            builder.append(SPACE);
            builder.append(wordsList[j]);
        }

        return builder.toString().trim();
    }
}
