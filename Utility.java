
public class Utility 
{
	public static int numberOfOccurences(String str, char character)
	{
		int counter = 0;
		char [] charArray = str.toCharArray();
		for(int i = 0; i < charArray.length; i++)
		{
			if(charArray[i] == character)
			{
				counter++;
			}
		}
		return counter;
	}
}
