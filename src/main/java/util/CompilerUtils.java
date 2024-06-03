package util;

import pojo.LR0State;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class CompilerUtils {
    final static Re r = new Re();
    final static Wr w = new Wr();

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

    static void printStep(Stack<Integer> stateStack, Stack<Character> symbolStack, int idx, String string) {
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

    static void analyzeString(List<LR0State> states, String string) {
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

            printStep(stateStack, symbolStack, idx, string);

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

}
