package gt.practice.algos.arrays;

public class ReverseWordsInString {
    public static void main(String[] args) {
        String s = "To be or not to be";
        char[] chars = s.toCharArray();

        int start=0;
        for (int i=0; i < chars.length;++i) {
            if (chars[i] == ' ' && i > start) {
                reverse(chars, start, i - 1);
                start = i + 1;
            } else if (chars[i] == ' ' && i == start){
                start = i;
            } else if (i == chars.length - 1) {
                reverse(chars, start, i);
            }
        }

        reverse(chars, 0 , chars.length - 1);

        System.out.println(chars);
    }

    private static void reverse(char[] a, int start, int end) {
        for (int i=0; i <= (end - start)/2; ++i) {
            char temp = a[i+start];
            a[i+start] = a[end -i];
            a[end-i] = temp;
        }
    }
}
