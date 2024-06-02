package util;

import util.CompilerUtils;

import java.util.*;

/**
 * 求 First 集<br>
 * 默认非终结符为大写字母，终结符为小写字母与 #
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class FirstBuilder {
    /**
     * 文法 S->ab
     */
    private HashMap<Character, List<String>> wenfa;

    public FirstBuilder(HashMap<Character, List<String>> wenfa) {
        this.wenfa = wenfa;
    }

    /**
     * 用于计算形如 FIRST("aAb")
     *
     * @param right "aAb"
     * @return .
     */
    public HashSet<Character> build(String right) {
        HashSet<Character> res = new HashSet<>();
        for (int i = 0; i < right.length(); i++) {
            char ch = right.charAt(i);
            res.addAll(innerBuild(ch));
        }
        return res;
    }

    /**
     * 尝试对非终结符 ch 推导
     *
     * @param ch 非终结符
     * @return .
     */
    private HashSet<Character> innerBuild(char ch) {
        HashSet<Character> res = new HashSet<>();
        if (CompilerUtils.isTerminal(ch)) {
            res.add(ch);
            return res;
        }

        // ch 是非终结符
        /*
        1. 找到 ch -> 形式的所有产生式;
        2.
         */
        List<String> productions = wenfa.get(ch);
        if (productions != null) {
            for (String production : productions) {
                if (production.isEmpty()) {
                    break;
                }
                res.addAll(build(production));
            }
        }
        return res;
    }

}
