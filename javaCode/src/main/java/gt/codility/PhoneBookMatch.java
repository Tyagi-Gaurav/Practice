package gt.codility;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.naturalOrder;

public class PhoneBookMatch {

    private static final String NO_CONTACT = "NO CONTACT";;

    public static void main(String[] args) {
        System.out.println(solution(null,
                new String[]{"999999999", "777888999"}, "88999"));
        System.out.println(solution(null, null, "88999"));
        System.out.println(solution(new String[]{"999999999", "777888999"}, null, "88999"));
        System.out.println(solution(new String[]{"pim", "pom"},
                new String[]{"999999999", "777888999"}, "88999"));
        System.out.println(solution(new String[]{"sander","amy","ann","michael"},
                new String[]{"123456789","234567890","789123456","123123123"}, "1"));
        System.out.println(solution(new String[]{"adam","eva","leo"},
                new String[]{"121212121","111111111","444555666"}, "112"));
        System.out.println(solution(new String[]{"ayam","adam","leo"},
                new String[]{"121212121","111111111","444555666"}, "1"));
        System.out.println(solution(new String[]{"ayam","adam","a"},
                new String[]{"121212121","111111111","444551666"}, "1"));
    }

    public static String solution(String[] A, String[] B, String P) {
        if (Objects.isNull(A) || Objects.isNull(B) ||
                A.length != B.length) {
            return NO_CONTACT;
        }

        Optional<String> min = IntStream.range(0, B.length)
                .filter(i -> B[i].contains(P))
                .mapToObj(i -> A[i]).min(naturalOrder());


        return min.orElse(NO_CONTACT);
    }
}
