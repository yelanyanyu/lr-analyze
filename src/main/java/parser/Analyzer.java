package parser;

import pojo.LRState;
import util.FirstBuilder;

import java.util.*;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public abstract class Analyzer {
    protected List<LRState> states;
    protected HashMap<Integer, List<String>> initProjects = new HashMap<>();
    protected Map<Integer, String> wenfa;
    protected Map<Character, List<String>> wenfa2;
    protected FirstBuilder firstBuilder;

    public Analyzer(Map<Integer, String> wenfa) {
        this.wenfa = wenfa;
        this.states = new ArrayList<>();
        this.wenfa2 = new HashMap<>();
        // init initProjects

        initWenfa2();
    }

    protected abstract void initWenfa2();

    public abstract void parse(String input);

    /**
     * gotoFunction template
     *
     * @param item .
     * @return .
     */
    protected abstract List<String> processItem(String item);

    /**
     * gotoFunction template
     *
     * @param transitions .
     * @return .
     */
    protected abstract void postProcessTransitions(HashMap<Character, List<String>> transitions);

    protected HashMap<Character, List<String>> gotoFunction(List<String> items) {
        HashMap<Character, List<String>> transitions = new HashMap<>();
        for (String item : items) {
            int dotPos = item.indexOf('.');
            if (dotPos != -1 && dotPos + 1 < item.length()) {
                char afterDot = item.charAt(dotPos + 1);
                List<String> newItemList = processItem(item);

                List<String> list = transitions.computeIfAbsent(afterDot, k -> new ArrayList<>());
                list.addAll(newItemList);
            }
        }

        postProcessTransitions(transitions);
        return transitions;
    }

    protected List<String> closure(List<String> items) {
        HashSet<String> set = new HashSet<>(items);
        boolean added = true;

        while (added) {
            added = false;
            List<String> newItems = new ArrayList<>();
            for (String item : items) {
                int dotPos = item.indexOf('.');
                if (dotPos != -1 && dotPos + 1 < item.length()) {
                    char symbolAfterDot = item.charAt(dotPos + 1);
                    if (Character.isUpperCase(symbolAfterDot)) {
                        newItems.addAll(applyClosureRule(symbolAfterDot, set));
                    }
                }
            }
            if (!newItems.isEmpty()) {
                items.addAll(newItems);
                set.addAll(newItems);
                added = true;
            }
        }
        return items;
    }

    protected abstract List<String> applyClosureRule(char symbolAfterDot, Set<String> existingItems);
}
