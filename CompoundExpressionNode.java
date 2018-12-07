import java.util.LinkedList;

/**
 * An abstract "helper" class for the operator classes to reduce the redundancy for similar methods.
 */

public abstract class CompoundExpressionNode extends ExpressionNode implements CompoundExpression
{	
	public CompoundExpressionNode(String data)
	{
		super(data);
	}

	/**
	 * Adds the passed in expression as a child
	 * @param expression the child expression that must be added
	 */
	
	public void addSubexpression(Expression expression)
	{
		_children.add((ExpressionNode)expression);
	}

	/**
	 * Gets the list of expressions
	 * @return the list of expressions
	 */
	
	public LinkedList<ExpressionNode> getSubexpressions()
	{
		return _children;
	}
}
