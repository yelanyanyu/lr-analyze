import parser.Analyzer;
import parser.LR0Analyzer;
import pojo.LR0State;
import util.CompilerUtils;
import util.FirstBuilder;
import util.Re;
import util.Wr;

import java.util.*;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class Main {
    final static Re r = new Re();
    final static Wr w = new Wr();
    final static HashMap<Integer, List<String>> initProjects = new HashMap<>();
    final static HashMap<Integer, String> wenfa = new HashMap<>();
    static Analyzer lr0;
    static Analyzer lr1;
    static String string;
    static int accept;

    static void input() {
        string = r.next();
        int idx1 = 0;
        while (r.hasNext()) {
            String next = r.next();
            if (!next.contains("->")) {
                throw new RuntimeException("incorrect wenfa");
            }
            wenfa.put(idx1++, next);
        }

        lr0 = new LR0Analyzer(wenfa);
    }

    static void printStates(List<LR0State> states) {
        for (LR0State state : states) {
            w.println("state " + state.id + " ACTION: " + state.action);
            accept = state.id;
            for (String project : state.projects) {
                w.println("\t " + project);
            }

            w.println("----------------------");
            state.transitions.forEach(((character, integer) -> {
                String out = "\t " + character + " -> State " + integer;
                if (Character.isUpperCase(character)) {
                    out = out + " GOTO";
                } else {
                    out = out + " ACTION";
                }
                w.println(out);
            }));

            w.println("");
        }
    }

    public static void main(String[] args) {
        input();
//        w.println("project:");
//        for (int i = 0; i < initProjects.size(); i++) {
//            w.println(initProjects.get(i));
//        }
//
//        // 记录不同的项目集
//        List<LR0State> states = new ArrayList<>();
//        // 反向索引, 可以通过项目集找到对应的状态
//        HashMap<List<String>, Integer> stateMap = new HashMap<>();
//        int stateCount = 0;
//
//        LR0State startState = new LR0State();
//        startState.id = stateCount++;
//        startState.projects = closure(initProjects.get(0));
//        states.add(startState);
//        stateMap.put(startState.projects, startState.id);
//
//        // 构建 DFA
//        for (int i = 0; i < states.size(); i++) {
//            LR0State state = states.get(i);
//            HashMap<Character, List<String>> transitions = gotoFunction(state.projects);
//
//            for (Map.Entry<Character, List<String>> entry : transitions.entrySet()) {
//                Character symbol = entry.getKey();
//                List<String> items = entry.getValue();
//                List<String> newStateProject = closure(items);
//                if (!stateMap.containsKey(newStateProject)) {
//                    LR0State newState = new LR0State();
//                    newState.id = stateCount++;
//                    newState.projects = newStateProject;
//                    states.add(newState);
//                    stateMap.put(newStateProject, newState.id);
//                    state.transitions.put(symbol, newState.id);
//                } else {
//                    state.transitions.put(symbol, stateMap.get(newStateProject));
//                }
//            }
//            state.setAction();
//        }
//
//        w.println("==========================================");
//        // 判断是否为 LR(0) 文法
//        boolean lr0 = isLR0(states);
//        if (lr0) {
//            w.println("是 LR(0) 文法");
//        } else {
//            w.println("不是 LR(0) 文法");
//        }
//        w.println("==========================================");
//        // 打印LR(0)分析表
//        printStates(states);
//        w.println("====================================");
//        // 字符串分析
//        analyzeString(states);
        w.close();
    }

    static void analyzeString(List<LR0State> states) {
        Stack<Integer> stateStack = new Stack<>();
        Stack<Character> symbolStack = new Stack<>();
        stateStack.push(0);
        symbolStack.push('#');
        int idx = 0;
        w.println("stateStack| \t symbolStack| \t inputString| \t action| \t goto");
        while (true) {
            int currentState = stateStack.peek();
            char currentChar = string.charAt(idx);
            LR0State curState = states.get(currentState);
            String action = curState.action;

            Integer nextStateId = curState.transitions.get(currentChar);

            printStep(stateStack, symbolStack, idx);

            if (action.equals("acc")) {
                break;
            } else if (action.equals("规约")) {
                String item = curState.projects.get(0);
                item = item.replace(".", "");
                String right = CompilerUtils.getRight(item);
                char left = CompilerUtils.getLeft(item).charAt(0);

                for (int i = 0; i < right.length(); i++) {
                    stateStack.pop();
                    symbolStack.pop();
                }

                if (!stateStack.isEmpty()) {
                    LR0State topState = states.get(stateStack.peek());
                    LR0State next = states.get(topState.transitions.get(left));
                    stateStack.push(next.id);
                    symbolStack.push(left);
                } else {
                    w.println("err");
                    break;
                }
            } else if (action.equals("移进")) {
                stateStack.push(nextStateId);
                symbolStack.push(currentChar);
                idx++;
            }
        }
    }

    static void printStep(Stack<Integer> stateStack, Stack<Character> symbolStack, int idx) {
        StringBuilder stateStackStr = new StringBuilder();
        for (Integer integer : stateStack) {
            stateStackStr.append(integer).append(" ");
        }
        StringBuilder symbolStackStr = new StringBuilder();
        for (Character character : symbolStack) {
            symbolStackStr.append(character).append(" ");
        }
        w.println(stateStackStr + " | \t " + symbolStackStr + " | \t "
                + string.substring(idx));
    }

    static boolean isLR0(List<LR0State> states) {
        for (LR0State state : states) {
            List<String> shiftItems = state.getShiftItems();
            List<String> reduceItems = state.getReduceItems();

            if (!shiftItems.isEmpty() && !reduceItems.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 移动该项目集 items 内的所有‘.’, 进行移进和归约, 构建新的状态
     *
     * @param items 项目集
     * @return 新的状态
     */
    static HashMap<Character, List<String>> gotoFunction(List<String> items) {
        HashMap<Character, List<String>> transitions = new HashMap<>();
        for (String item : items) {
            int dosPos = item.indexOf('.');
            if (dosPos != -1 && dosPos + 1 < item.length()) {
                char afterDot = item.charAt(dosPos + 1);
                String newItem = item;
                newItem = CompilerUtils.swapString(newItem, dosPos, dosPos + 1);

                List<String> list = transitions.get(afterDot);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(newItem);
                transitions.put(afterDot, list);
            }
        }

        // 去重
        transitions.forEach((ch, list) -> {
            CompilerUtils.distinctList(list);
        });


        return transitions;
    }

    /**
     * 求项目集 items 的闭包, 若 '.' 后面是非终结符，那么就将文法中所有的以该非终结符号开头的<br>
     * 文法加入该项目集
     *
     * @param items 项目集
     * @return 新的项目集
     */
    static List<String> closure(List<String> items) {
        HashSet<String> set = new HashSet<>(items);
        boolean added = true;

        while (added) {
            added = false;
            List<String> newItems = new ArrayList<>();
            for (String item : items) {
                int dosPos = item.indexOf('.');
                if (dosPos != -1 && dosPos + 1 < item.length()) {
                    char symbolAfterDot = item.charAt(dosPos + 1);

                    if (Character.isUpperCase(symbolAfterDot)) {
                        // 如果是非终结符, 找到所有符合的文法, 并将文法转化为项目
                        for (Map.Entry<Integer, List<String>> entry : initProjects.entrySet()) {
                            Integer idx = entry.getKey();
                            List<String> list = entry.getValue();
                            for (String grammar : list) {
                                if (grammar.charAt(0) == symbolAfterDot) {
                                    // 将该文法转化为项目
                                    // 确保不重复加入文法
                                    if (!set.contains(grammar)) {
                                        newItems.add(grammar);
                                        set.add(grammar);
                                        added = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            items.addAll(newItems);
        }
        return items;
    }
}


