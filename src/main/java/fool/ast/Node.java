package fool.ast;

import fool.visitor.Visitor;

public interface Node {

    void accept(Visitor v);

}
