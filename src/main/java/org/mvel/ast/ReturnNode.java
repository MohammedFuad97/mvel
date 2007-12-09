package org.mvel.ast;

import static org.mvel.MVEL.eval;
import org.mvel.compiler.Accessor;
import org.mvel.compiler.EndWithValue;
import org.mvel.integration.VariableResolverFactory;
import static org.mvel.util.ParseTools.subCompileExpression;

/**
 * @author Christopher Brock
 */
public class ReturnNode extends ASTNode {
    private boolean graceful = false;

    public ReturnNode(char[] expr, int fields) {
        super(expr, fields);
        setAccessor((Accessor) subCompileExpression(expr));
    }


    public Object getReducedValueAccelerated(Object ctx, Object thisValue, VariableResolverFactory factory) {
        if (accessor == null) {
            setAccessor((Accessor) subCompileExpression(this.name));
        }
        if (graceful) {
            return accessor.getValue(ctx, thisValue, factory);
        }
        else {
            throw new EndWithValue(accessor.getValue(ctx, thisValue, factory));
        }
    }

    public Object getReducedValue(Object ctx, Object thisValue, VariableResolverFactory factory) {
        throw new EndWithValue(eval(this.name, ctx, factory));
    }

    public boolean isGraceful() {
        return graceful;
    }

    public void setGraceful(boolean graceful) {
        this.graceful = graceful;
    }
}