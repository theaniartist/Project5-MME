import java.util.LinkedList;

public abstract class ExpressionNode implements Expression
{
		
		public String _data;
		private CompoundExpression _parent;
		protected LinkedList<ExpressionNode> _children;
		
		public ExpressionNode(String data)
		{
			_data = data;
			_parent = null;
			_children = new LinkedList<ExpressionNode>();
		}
		
		/**
		 * Method that recursively calls itself to create a String of type Expression and its children
		 * that starts at the given indent level.
		 * @param stringBuilder the stringBuilder that appends a name and a new line to create a String
		 * @param indentLevel the current indentation level
		 */
		public void convertToString(StringBuilder stringBuilder, int indentLevel)
		{
			
			indent(stringBuilder, indentLevel);

			stringBuilder.append(_data);

			stringBuilder.append('\n');

			for (Expression expr : _children) {
				expr.convertToString(stringBuilder, indentLevel + 1);
			}
			
			System.out.println(stringBuilder.toString());

		}
		
		/**
		 * Helper method for indentation for StirngBuilder
		 * @param stringBuilder the stringBuilder that appends each tab character
		 * @param indentLevel the number of tabs that must be appended to StirngBuilder
		 */


		public static void indent(StringBuilder stringBuilder, int indentLevel) {

			for(int i = 0; i < indentLevel; i++) {

				stringBuilder.append('\t');

			}

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

		/**
		 * Method helps flatten the expression.
		 *
		 */

		public void flatten()
		{
			if (_children.size() > 0) 
			{
				int i = 0;
				while(i < _children.size())
				{
					ExpressionNode subExpr = _children.get(i);
					subExpr.flatten();
					if (_data.equals(subExpr._data)) 
					{
						_children.addAll(subExpr._children);
						_children.remove(subExpr);
					}
					i++;
				}				
			}
		}
}
