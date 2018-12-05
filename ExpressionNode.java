import java.util.LinkedList;

public abstract class ExpressionNode implements Expression
{
		
		private String _data;
		private LinkedList<Expression> _children;
		private CompoundExpression _parent;
		
		public ExpressionNode(String data)
		{
			_data = data;
			_children = new LinkedList<Expression>();
			_parent = null;
		}
		
		public CompoundExpression getParent()
		{
			return _parent;
		}
		
		public void setParent (CompoundExpression parent)
		{
			_parent = parent;
		}
		
		public Expression deepCopy()
		{
			return null;
		}
		
		public void flatten()
		{
			
		}
		
		public void convertToString(StringBuilder stringBuilder, int indentLevel)
		{
			
		}
}
