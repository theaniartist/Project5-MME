import java.util.LinkedList;

public abstract class CompoundExpressionNode extends ExpressionNode implements CompoundExpression
{	
	public CompoundExpressionNode(String data)
	{
		super(data);
	}
	
	public void addSubexpression(Expression expression)
	{
		_children.add((ExpressionNode)expression);
	}
	
	public LinkedList<ExpressionNode> getSubexpressions()
	{
		return _children;
	}
}
