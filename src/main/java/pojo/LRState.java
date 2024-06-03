package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class LRState {
    public int id;
    public List<String> projects = new ArrayList<>();
    public HashMap<Character, Integer> transitions = new HashMap<>();
    public String action;
}
