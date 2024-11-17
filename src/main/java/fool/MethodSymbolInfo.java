package fool;

import java.util.List;

public class MethodSymbolInfo extends SymbolInfo {
    private List<ParameterSymbolInfo> parameters;

    public MethodSymbolInfo(String name, Type returnType, List<ParameterSymbolInfo> parameters) {
        super(name, returnType);
        this.parameters = parameters;
    }

    public List<ParameterSymbolInfo> getParameters() {
        return parameters;
    }
}
