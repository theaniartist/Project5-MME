import java.util.LinkedList;
import java.util.Stack;

public class Main 
{
	public static void main(String[] args) 
	{
		SimpleExpressionParser parser = new SimpleExpressionParser();
		//CompoundExpressionNode expression = (CompoundExpressionNode) parser.parseExpression("((9))*x+8+(9*80+9)");
		CompoundExpressionNode expression = (CompoundExpressionNode) parser.parseExpression("1+1*(2+1)");
		//System.out.println(((ExpressionNode)expression.getSubexpressions().get(0))._data);
		expression.convertToString(new StringBuilder(), 0);
		ExpressionNode node = (((ExpressionNode) expression.getSubexpressions().get(1)));
		//System.out.println(node._data);
		//System.out.println(((ExpressionNode)((CompoundExpressionNode)((ExpressionNode)((CompoundExpressionNode) node).getSubexpressions().get(1))).getSubexpressions().get(0))._data);
	}
}
