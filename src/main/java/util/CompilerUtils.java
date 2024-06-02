package util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class CompilerUtils {
    public static String getLeft(String string) {
        return string.substring(0, string.indexOf("-"));
    }

    public static String getRight(String right) {
        return right.substring(right.indexOf(">") + 1);
    }

    public static void distinctList(List<String> list) {
        Set<String> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
    }

    public static String swapString(String str, int p1, int p2) {
        char[] charArray = str.toCharArray();
        char t = charArray[p1];
        charArray[p1] = charArray[p2];
        charArray[p2] = t;
        return String.valueOf(charArray);
    }

    public static boolean isTerminal(char ch) {
        if (Character.isLowerCase(ch)) {
            return true;
        }

        switch (ch) {
            case '#':
                return true;
            default:
                return false;
        }
    }

    public static boolean isNonTerminal(char ch) {
        return Character.isUpperCase(ch);
    }

}
