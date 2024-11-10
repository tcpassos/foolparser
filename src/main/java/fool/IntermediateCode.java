package fool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediateCode {

    private List<CodeFragment> mainCode;
    private Map<String, CodeFragment> labels;

    public IntermediateCode() {
        this.mainCode = new ArrayList<>();
        this.labels = new HashMap<>();
    }

    private int labelCount = 0;

    public void append(CodeFragment code) {
        mainCode.add(code);
    }

    public String appendLabel(CodeFragment code) {
        String label = "L" + labelCount++;
        appendLabel(label, code);
        return label;
    }

    public void appendLabel(String label, CodeFragment code) {
        if (labels.containsKey(label)) {
            throw new IllegalArgumentException("Label already exists: " + label);
        }
        labels.put(label, code);
    }

    public String generate() {
        StringBuilder sb = new StringBuilder();

        mainCode.stream().filter(c -> c != null)
                         .map(CodeFragment::generate)
                         .filter(s -> !s.isEmpty())
                         .forEach(s -> sb.append(s).append("\n"));

        labels.forEach((label, code) -> {
            sb.append("\n").append(label).append(":\n");
            sb.append(code.generate()).append("\n");
        });

        return sb.toString();
    }

}
