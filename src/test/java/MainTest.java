import org.junit.Test;
import util.FirstBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class MainTest {
    @Test
    public void t1() {
        HashMap<Character, List<String>> wenfa = new HashMap<>();
        wenfa.put('S', Arrays.asList("AB", "#"));
        wenfa.put('A', Arrays.asList("a"));
        wenfa.put('B', Arrays.asList("b"));

        FirstBuilder firstBuilder = new FirstBuilder(wenfa);
        System.out.println("FIRST(S): " + firstBuilder.build("S")); // 应包含 {a, b}
        System.out.println("FIRST(A): " + firstBuilder.build("A")); // 应包含 {a, ε}
        System.out.println("FIRST(B): " + firstBuilder.build("B")); // 应包含 {b}
    }
}
