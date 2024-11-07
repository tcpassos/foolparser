package fool;

public class Expression implements CodeFragment {
    public String op;
    public String arg1;
    public String arg2;
    public String result;

    public Expression(String result) {
        this(null, null, null, result);
    }

    public Expression(String op, String arg1, String result) {
        this(op, arg1, null, result);
    }

    public Expression(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    @Override
    public String generate() {
        if (op == null || op.isEmpty()) {
            return "";
        } else if (arg2 == null || arg2.isEmpty()) {
            return String.format("%s = %s %s", result, op, arg1);
        } else {
            return String.format("%s = %s %s %s", result, arg1, op, arg2);
        }
    }
}