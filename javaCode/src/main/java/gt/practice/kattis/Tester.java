package gt.practice.kattis;

import java.io.*;
import java.util.*;

public class Tester {

    /**
     * Complete the function below.
     * DO NOT MODIFY anything outside this method. 
     */
    static int[] balancedOrNot(String[] expressions, int[] maxReplacements) {
        int[] output = new int[expressions.length];

        for (int i = 0; i < expressions.length; i++) {
            int balanced = 0;
            int maxReplace = i < maxReplacements.length ? maxReplacements[i] : 0;

            char[] line = expressions[i].toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] == '<') {
                    balanced ++;
                } else if (line[j] == '>') {
                    balanced--;
                    if (balanced < 0 &&  maxReplace > 0) {
                        balanced++;
                        maxReplace--;
                    } else if (balanced < 0 && maxReplace == 0){
                        break;
                    }
                }
            }

            output[i] = balanced == 0 ? 1 : 0;

        }

        return output;
    }

    /**
     * DO NOT MODIFY THIS METHOD!
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int[] res;

        int _expressions_size = 0;
        _expressions_size = Integer.parseInt(in.nextLine().trim());
        String[] _expressions = new String[_expressions_size];
        String _expressions_item;
        for (int _expressions_i = 0; _expressions_i < _expressions_size; _expressions_i++) {
            try {
                _expressions_item = in.nextLine();
            } catch (Exception e) {
                _expressions_item = null;
            }
            _expressions[_expressions_i] = _expressions_item;
        }

        int _maxReplacements_size = 0;
        _maxReplacements_size = Integer.parseInt(in.nextLine().trim());
        int[] _maxReplacements = new int[_maxReplacements_size];
        int _maxReplacements_item;
        for (int _maxReplacements_i = 0; _maxReplacements_i < _maxReplacements_size; _maxReplacements_i++) {
            _maxReplacements_item = Integer.parseInt(in.nextLine().trim());
            _maxReplacements[_maxReplacements_i] = _maxReplacements_item;
        }

        res = balancedOrNot(_expressions, _maxReplacements);
        for (int res_i = 0; res_i < res.length; res_i++) {
            System.out.println(String.valueOf(res[res_i]));
        }
    }
}