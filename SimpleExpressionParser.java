import java.util.function.Function;

/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
	/*
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}
	
	private static boolean isA(String str)
	{
		if(isValidRule(str, '+', SimpleExpressionParser::isA, SimpleExpressionParser::isM))
		{
			return true;
		}
		else if(isM(str))
		{
			return true;
		}
		return false;
	}
	
	private static boolean isM(String str)
	{
		if(isValidRule(str, '*', SimpleExpressionParser::isM, SimpleExpressionParser::isM))
		{
			return true;
		}
		else if(isX(str))
		{
			return true;
		}
		return false;
	}
	
	private static boolean isX(String str)
	{
		if(isValidRule(str, '*', SimpleExpressionParser::isM, SimpleExpressionParser::isM))
		{
			return true;
		}
		else if(isX(str))
		{
			return true;
		}
		return false;
	}

	private Expression parseE(String str)
	{
		if(isA(str))
		{
			return true;
		}
		else if(isX(str))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private Expression parseA(String str)
	{
		if(parseProductionRule(str, '+', SimpleExpressionParser::parseA, SimpleExpressionParser::parseM))
		{
			return true;
		}
		else if(parseM(str))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private Expression parseM(String str) {
		// TODO Auto-generated method stub
		return false;
	}

	private Expression parseX()
	{

	}

	private static boolean isValidRule(String str, char operator, Function<String, Boolean> testLeftSubexpression, Function<String, Boolean> testRightSubexpression)
	{
		for(int i = 1; i < str.length() - 1; i++)
		{
			if(str.charAt(i) == operator && testLeftSubexpression.apply(str.substring(0, i)) && testRightSubexpression.apply(str.substring(i + 1)))
			{
				return true;
			}
		}
		return false;
	}

	protected Expression parseExpression (String str) {
		Expression expression = parseE(str);
		
		return null;
	}
	
	public Expression deepCopy() {

		return null;

	}

}
