package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yelanyanyu@zjxu.edu.cn
 * @version 1.0
 */
public class LR0State extends LRState {
    /**
     * 获取移进项目：圆点后是终结符的项目
     */
    public List<String> getShiftItems() {
        List<String> shiftItems = new ArrayList<>();
        for (String project : projects) {
            int dotIndex = project.indexOf('.');
            if (dotIndex != -1 && dotIndex + 1 < project.length()) {
                char afterDot = project.charAt(dotIndex + 1);
                if (!Character.isUpperCase(afterDot)) { // 终结符检查
                    shiftItems.add(project);
                }
            }
        }
        return shiftItems;
    }

    public void setAction() {
        List<String> shiftItems = this.getShiftItems();
        List<String> reduceItems = this.getReduceItems();

        if (projects.size() == 1 && projects.contains("X->S.")) {
            action = "acc";
            return;
        }
        if (!shiftItems.isEmpty() && !reduceItems.isEmpty()) {
            action = "冲突";
        } else if (!shiftItems.isEmpty()) {
            action = "移进";
        } else if (!reduceItems.isEmpty()) {
            action = "规约";
        }
    }

    /**
     * 获取归约项目：圆点在最右的项目
     */
    public List<String> getReduceItems() {
        List<String> reduceItems = new ArrayList<>();
        for (String project : projects) {
            if (project.endsWith(".")) {
                // 圆点在最右
                reduceItems.add(project);
            }
        }
        return reduceItems;
    }
}
