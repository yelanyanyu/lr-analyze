package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class LR1Analyzer extends Analyzer {

    public LR1Analyzer(Map<Integer, String> wenfa) {
        super(wenfa);
    }

    @Override
    public void parse(String input) {

    }

    @Override
    protected List<String> processItem(String item, int dosPos) {
        return null;
    }

    @Override
    protected void postProcessTransitions(HashMap<Character, List<String>> transitions) {

    }

    @Override
    protected List<String> applyClosureRule(char symbolAfterDot, Set<String> existingItems) {
        return null;
    }
}
