import java.util.Arrays;
import java.util.Collections;
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
	
	private static boolean isE(String str)
	{
		if(isX(str))
		{
			return true;
		}
		else if(isA(str))
		{
			return true;
		}
		return false;
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
		if(str.charAt(0) == '(' && findEndOfParenthetical(str) != -1)
		{
			if(isE(str.substring(1, findEndOfParenthetical(str))))
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
	
	private static int findEndOfParenthetical(String str)
	{
		char [] charArray = str.toCharArray();
		int occurencesOfOpenParentheses = Utility.numberOfOccurences(str, '(');
		int occurencesOfClosedParentheses = Utility.numberOfOccurences(str, ')');
		if(occurencesOfOpenParentheses == occurencesOfClosedParentheses)
		{
			int countClosedParentheses = 0;
			for(int i = 0; i < charArray.length; i++)
			{
				if(charArray[i] == ')')
				{
					countClosedParentheses++;
					if(countClosedParentheses == occurencesOfClosedParentheses && occurencesOfOpenParentheses > 0 && occurencesOfClosedParentheses > 0)
					{
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	private static Expression parseE(String str)
	{
		if(isX(str))
		{
			return parseX(str);
		}
		else if(isA(str))
		{
			return parseA(str);
		}
		return null;
	}

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

	private static Expression parseM(String str)
	{
		if(isX(str))
		{
			return parseX(str);
		}
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

	private static Expression parseX(String str)
	{
		if(str.charAt(0) == '(' && findEndOfParenthetical(str) != -1)
		{
			if(isE(str.substring(1, findEndOfParenthetical(str))))
			{
				ParentheticalExpression parentheticalNode = new ParentheticalExpression("()");
				parentheticalNode.addSubexpression(parseE(str.substring(1, findEndOfParenthetical(str))));
				return parentheticalNode;
			}
		}
		else if(isL(str))
		{
			return new LiteralExpression(str);
		}
		return null;
	}

	protected Expression parseExpression (String str) {
		for(char character : str.toCharArray())
		{
			if(character != '(' && character != ')')
			{
				return parseE(str); 
			}
		}
		
		return null;
	}
	
	public Expression deepCopy() {

		return null;

	}

}
