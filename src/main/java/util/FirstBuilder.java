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
    private Map<Character, List<String>> wenfa;

    public FirstBuilder(Map<Character, List<String>> wenfa) {
        this.wenfa = wenfa;
    }

    /**
     * 用于计算形如 FIRST("aAb")
     *
     * @param right "aAb"
     * @return .
     */
    public Set<Character> build(String right) {
        Set<Character> res = new HashSet<>();
        char ch = right.charAt(0);
        // TODO: 递归错误
        res.addAll(innerBuild(ch));
        return res;
    }

    /**
     * 尝试对非终结符 ch 推导
     *
     * @param ch 非终结符
     * @return .
     */
    private Set<Character> innerBuild(char ch) {
        Set<Character> res = new HashSet<>();
        /*
            1. 找到 ch -> 形式的所有产生式;
            2.
         */
        List<String> productions = wenfa.get(ch);
        if (productions != null) {
            for (String production : productions) {
                char c = CompilerUtils.getRight(production).charAt(0);
                if (CompilerUtils.isTerminal(c)) {
                    res.add(c);
                    continue;
                }
                if (production.isEmpty()) {
                    break;
                }
                res.addAll(build(production));
            }
        }
        return res;
    }

}
