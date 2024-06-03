import parser.Analyzer;
import parser.LR0Analyzer;
import parser.LR1Analyzer;
import pojo.LR0State;
import pojo.LRState;
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
        lr1 = new LR1Analyzer(wenfa);
    }

    static void printStates(List<LRState> states) {
        for (LRState state : states) {
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

        w.println("LR0: ============================================");

        lr0.parse(string);
        List<LRState> lr0States = lr0.getStates();
        printStates(lr0States);

        w.println("LR1: ============================================");
        lr1.parse(string);
        List<LRState> lr1States = lr1.getStates();
        printStates(lr1States);

        w.close();
    }
}


