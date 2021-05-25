package gt.codility;

public class PhoneNumberFormat {
    public static void main(String[] args) {
        System.out.println(formatPhone("92"));
        System.out.println(formatPhone("921"));
        System.out.println(formatPhone("00-44   48   5555  8631"));
        System.out.println(formatPhone("0 - 22 1985--324"));
        System.out.println(formatPhone("555372654"));
        System.out.println(formatPhone("11"));
        System.out.println(formatPhone("2374673264324783247382479832563284727472198782174982747219479812473647297382647632478678326476327647"));
        System.out.println(formatPhone("23746- ----- 324873824798 ----    435798347985 ----- -5-5-5-5-3-3-3-2-2-5-6--7-7-8-8-8--9-9-9-9-9-33"));
        System.out.println(formatPhone("9214"));
        System.out.println(formatPhone("92145"));
    }

    public static String formatPhone(String input) {
        /*
            1. Extract all the digits.
            2. For each group of 3 output with a dash.
         */
        String stringWithDigits = input.replaceAll("[ -]", "");
        StringBuilder stringBuilder = new StringBuilder(stringWithDigits);
        int index = 0;

        while (index < stringBuilder.length()) {
            int remainingDigits = stringBuilder.length() - index;

            if (remainingDigits >= 5) {
                index += 3;
                stringBuilder.insert(index, "-");
            } else if (remainingDigits >= 4) {
                index += 2;
                stringBuilder.insert(index, "-");
            }

            index++;
        }

        return stringBuilder.toString();
    }
}
