package fool;

public class CodeContext {
    private int tempCount = 0;
    private int labelCount = 0;

    public String newTemp() {
        return "t" + tempCount++;
    }

    public String newLabel(String prefix) {
        return prefix + "_" + labelCount++;
    }
}
