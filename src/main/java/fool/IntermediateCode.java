package fool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediateCode implements CodeFragment {

    private List<CodeFragment> mainCode;
    private Map<String, List<CodeFragment>> labels;

    public IntermediateCode() {
        this.mainCode = new ArrayList<>();
        this.labels = new HashMap<>();
    }

    private int labelCount = 0;

    public void append(CodeFragment code) {
        mainCode.add(code);
    }

    public void appendLabel(CodeFragment code) {
        String label = "L" + labelCount++;
        labels.get(label).add(code);
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();

        mainCode.stream().filter(c -> c != null)
                         .map(CodeFragment::generate)
                         .filter(s -> !s.isEmpty())
                         .forEach(s -> sb.append(s).append("\n"));

        labels.forEach((label, code) -> {
            sb.append(label).append(":\n");
            code.forEach(c -> sb.append(c.generate()).append("\n"));
        });

        return sb.toString();
    }

}
