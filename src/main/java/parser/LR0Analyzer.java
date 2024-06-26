package parser;

import pojo.LR0State;
import pojo.LRState;
import util.CompilerUtils;

import java.util.*;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class LR0Analyzer extends Analyzer {
    public LR0Analyzer(Map<Integer, String> wenfa) {
        super(wenfa);
    }

    @Override
    public void parse(String input) {
        // 初始化项目集和状态
        List<LRState> states = new ArrayList<>();
        HashMap<List<String>, Integer> stateMap = new HashMap<>();
        int stateCount = 0;

        // 创建初始状态
        LR0State startState = new LR0State();
        startState.id = stateCount++;
        startState.projects = closure(initProjects.get(0));
        states.add(startState);
        stateMap.put(startState.projects, startState.id);

        // 构建DFA
        for (int i = 0; i < states.size(); i++) {
            LR0State state = (LR0State) states.get(i);
            HashMap<Character, List<String>> transitions = gotoFunction(state.projects);

            for (Map.Entry<Character, List<String>> entry : transitions.entrySet()) {
                Character symbol = entry.getKey();
                List<String> items = entry.getValue();
                List<String> newStateProject = closure(items);
                if (!stateMap.containsKey(newStateProject)) {
                    LR0State newState = new LR0State();
                    newState.id = stateCount++;
                    newState.projects = newStateProject;
                    states.add(newState);
                    stateMap.put(newStateProject, newState.id);
                    state.transitions.put(symbol, newState.id);
                } else {
                    state.transitions.put(symbol, stateMap.get(newStateProject));
                }
            }
            state.setAction();
        }

        this.states = states;
    }

    @Override
    protected List<String> processItem(String item, int dosPos) {
        List<String> res = new ArrayList<>();
        String newItem = item;
        newItem = CompilerUtils.swapString(newItem, dosPos, dosPos + 1);

        res.add(newItem);
        return res;
    }

    @Override
    protected void postProcessTransitions(HashMap<Character, List<String>> transitions) {
        transitions.forEach((ch, list) -> {
            CompilerUtils.distinctList(list);
        });
    }

    @Override
    protected List<String> applyClosureRule(char symbolAfterDot, Set<String> existingItems) {
        List<String> newItems = new ArrayList<>();
        // 检查wenfa2中是否有以symbolAfterDot为左侧的产生式
        if (wenfa2.containsKey(symbolAfterDot)) {
            for (String production : wenfa2.get(symbolAfterDot)) {
                String newItem = symbolAfterDot + "->." + production;
                // 只有当新产生的项目不在现有项目集中时，才添加它
                if (!existingItems.contains(newItem)) {
                    newItems.add(newItem);
                }
            }
        }
        return newItems;
    }
}
