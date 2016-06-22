package Url;

import static org.junit.Assert.*;

import org.junit.Test;

public class testGetInput
{

	@Test
	public void testInput()
	{
		String input = Crawler.getInput();
		assertEquals("http://javahungry.blogspot.com/",input);
	}

}
