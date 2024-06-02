package util;

import java.util.HashMap;
import java.util.List;

/**
 * 完成各种文法之间的相互转换
 *
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class GrammarTransformer {
    private HashMap<Integer, String> integerStringGrammar;
    private HashMap<Character, List<String>> characterListGrammar;

    public GrammarTransformer(HashMap<Integer, String> integerStringGrammar) {
        this.integerStringGrammar = integerStringGrammar;
    }
}
