import java.util.LinkedList;

public abstract class CompoundExpressionNode extends ExpressionNode implements CompoundExpression
{	
	public CompoundExpressionNode(String data)
	{
		super(data);
		_children = new LinkedList<Expression>();
	}
	
	public void addSubexpression(Expression expression)
	{
		_children.add(expression);
	}
	
	public LinkedList<Expression> getSubexpressions()
	{
		return _children;
	}
}
