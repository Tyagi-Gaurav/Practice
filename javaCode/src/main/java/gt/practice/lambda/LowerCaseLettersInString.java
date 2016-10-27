package gt.practice.lambda;

public class LowerCaseLettersInString {
    public static void main(String[] args) {
        String test = "iuhsfiuhGHGHhufhjGGFFDFDdgdguygduyg";

        long charCount = test.chars()
                            .filter(Character::isLowerCase)
                            .count();
        System.out.println(charCount);
    }
}
