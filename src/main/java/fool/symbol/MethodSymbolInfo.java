package fool.symbol;

import java.util.List;

import fool.type.Type;

public class MethodSymbolInfo extends SymbolInfo {
    private List<ParameterSymbolInfo> parameters;

    public MethodSymbolInfo(String name, Type returnType, List<ParameterSymbolInfo> parameters) {
        super(name, returnType);
        this.parameters = parameters;
    }

    public List<ParameterSymbolInfo> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuilder params = new StringBuilder();
        params.append("(");
        for (int i = 0; i < parameters.size(); i++) {
            ParameterSymbolInfo param = parameters.get(i);
            params.append(param.getType()).append(" ").append(param.getName());
            if (i < parameters.size() - 1) {
                params.append(", ");
            }
        }
        params.append(")");
    
        return String.format("Method(name='%s', return=%s, params=%s)", getName(), getType(), params.toString());
    }
    
}
