package fool;

import java.util.HashMap;
import java.util.Map;

public class CodeContext {
    private int tempCount = 0;
    private int labelCount = 0;
    private Map<Node, String> tempNames;
    private Map<String, Node> subtrees;

    public CodeContext() {
        this.tempNames = new HashMap<>();
        this.subtrees = new HashMap<>();
    }
    
    public String getNodeTemp(Node node) {
        String temp = tempNames.get(node);
        if (temp == null) {
            temp = newTemp();
            tempNames.put(node, temp);
        }
        return temp;
    }

    public String getSubtreeLabel(Node node, String prefix) {
        String label = newLabel(prefix);
        subtrees.put(label, node);
        return label;
    }

    public Map<String, Node> getSubtrees() {
        return subtrees;
    }

    private String newLabel(String prefix) {
        return prefix + "_" + labelCount++;
    }

    private String newTemp() {
        return "t" + tempCount++;
    }
}
