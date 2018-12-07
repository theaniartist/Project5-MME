import java.util.Arrays;
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

	/**
	 * Method checks for the production rule of E: whether the expression contains an
	 * A or M terminal.
	 * @param str the expression that is being checked for production rule E
	 * @return a boolean value whether it does or does not pass the test for the production rule of E
	 */
	
	private static boolean isE(String str)
	{
		if(isA(str))
		{
			return true;
		}
		else if(isX(str))
		{
			return true;
		}
		return false;
	}

	/**
	 * Method checks for the production rule of A: whether the expression contains an
	 * additive case or contains a M terminal.
	 * @param str the expression that is being checked for the production rule of A
	 * @return a boolean value whether it does or does not pass the test for the production rule of A
	 */
	
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

	/**
	 * Method checks for the production rule of M: whether the expression contains a multiplicative
	 * case or contains a X terminal.
	 * @param str the expression that is being checked for the production rule of M.
	 * @return a boolean value whether it does or does not pass the test for the production rule of A
	 */


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

	/**
	 * Method checks for the production rule of X: whether the expression contains a parenthetical
	 * case or contains a L terminal.
	 * @param str the expression that is being checked for the production rule of X.
	 * @return a boolean value whether it does or does not pass the test for the production rule of X
	 */
	
	private static boolean isX(String str)
	{
		if(str.charAt(0) == '(' && str.charAt(str.toCharArray().length - 1) == ')')
		{
			if(isE(str.substring(1, str.toCharArray().length - 1)))
			{
				return true;
			}
		}
		else if(isL(str))
		{
			return true;
		}
		return false;
	}

	/**
	 * Method checks for the production rule of L: whether the expression contains a literal
	 * case of numbers or letters (from a-z). Also checks for whether the expression being passed
	 * in contains any of the array of characters that should not be considered as a literal.
	 * @param str the expression that is being checked for the production rule of L.
	 * @return a boolean value whether it does or does not pass the test for the production rule of L
	 */

	private static boolean isL(String str)
	{
		char [] unallowedCharacters = {'~', '`', '!', '@', '#', '$', '%', '^', '&', '(', ')', '-', '_', '=', '{', '[', '}', ']', ':', ';', '"', '\\', '\'', '?',
										'/', '>', '.', '<', ',', '|', '*', '+'}; 
		for(int i = 0; i < unallowedCharacters.length; i++)
		{
			for(int j = 0; j < str.toCharArray().length; j++)
			{
				if(str.toCharArray()[j] == unallowedCharacters[i])
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the expression being passed in is a valid expression that can be parsed.
	 * @param str the String representation of the expression
	 * @param operator the character representation of the operator that may be passed in (+,*,(), or a literal)
	 * @param testLeftSubexpression function that tests for the left part of the expression (before the operator)
	 * @param testRightSubexpression function that tests for the right part of the expression (after the operator)
	 * @return a boolean value of whether or not the expression that is being passed in is valid to parse
	 */
	
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

	/**
	 * Parses the expression by the production rule of E: checks if the subexpression contains either
	 * terminal A or M from the boolean methods (isA() or isM()).
	 * @param str the String representation of the expression
	 * @return the expression that is either being parsed through the production rule of A or M. If
	 * the expression cannot be parsed by production rules of A or M, it would return null.
	 */
	
	private static Expression parseE(String str)
	{
		if(isA(str))
		{
			return parseA(str);
		}
		else if(isM(str))
		{
			return parseM(str);
		}
		return null;
	}

	/**
	 * Parses the expression through the production rule A. Checks if the expression contains the operator '+'
	 * in the expression.
	 * @param str the expression that is either being parsed through the condition of A+M or by the production
	 * rule of M.
	 * @return the left and right side expression of the operator. Or returns the parsed expression from the
	 * production rule of M. If it does not parse through the production rule, return null.
	 */

	private static Expression parseA(String str)
	{
		AdditiveExpression additiveNode = null;
		if(isValidRule(str, '+', SimpleExpressionParser::isA, SimpleExpressionParser::isM))
		{
			additiveNode = new AdditiveExpression("+");
			additiveNode.addSubexpression(parseM(str.substring(0, str.indexOf('+'))));
			additiveNode.addSubexpression(parseA(str.substring(str.indexOf('+') + 1)));
			return additiveNode;
		}
		else if(isM(str))
		{
			return parseM(str);
		}
		return null;
	}

	/**
	 * Parses the expression through the production rule M. Checks if the expression contains the operator '*'
	 * in the expression.
	 * @param str the expression that is either being parsed through the condition of M*M or by the production
	 * rule of X.
	 * @return the left and right side expression of the operator. Or returns the parsed expression from the
	 * production rule of X. If it does not parse through the production rule, return null.
	 */

	private static Expression parseM(String str)
	{
		if(isValidRule(str, '*', SimpleExpressionParser::isM, SimpleExpressionParser::isM))
		{
			MultiplicativeExpression multipilicativeNode = new MultiplicativeExpression("*");
			multipilicativeNode.addSubexpression(parseM(str.substring(0, str.indexOf('*'))));
			multipilicativeNode.addSubexpression(parseM(str.substring(str.indexOf('*') + 1)));
			return multipilicativeNode;
		} 
		else if(isX(str))
		{
			return parseX(str);
		}
		return null;
	}

	/**
	 * Parses the expression through the production rule X. Checks if the expression contains the '(' and ')'
	 * in the expression.
	 * @param str the expression that is either being parsed through the condition of (E) or by the production
	 * rule of L.
	 * @return the left and right side expression of the operator. Or returns the parsed expression from the
	 * production rule of L. If it does not parse through the production rule, return null.
	 */

	private static Expression parseX(String str)
	{
		if(str.charAt(0) == '(' && str.charAt(str.toCharArray().length - 1) == ')')
		{
			if(isE(str.substring(1, str.toCharArray().length - 1)))
			{
				ParentheticalExpression parentheticalNode = new ParentheticalExpression("()");
				parentheticalNode.addSubexpression(parseE(str.substring(1, str.toCharArray().length - 1)));
				return parentheticalNode;
			}
		}
		else if(isL(str))
		{
			return new LiteralExpression(str);
		}
		return null;
	}

	/**
	 * Method parses the String representation of the expression that is being passed in. Parses through the
	 * first production rule: E, to check if it either contains the additive case (A+M) or contains an expression
	 * that can be parsed through the production rule of M
	 * @param str the String representation of the expression that is going to be parsed
	 * @return the parsed expression from the production rule methods
	 */

	protected Expression parseExpression (String str) {
		Expression expression = parseE(str);
		
		return expression;
	}

}
