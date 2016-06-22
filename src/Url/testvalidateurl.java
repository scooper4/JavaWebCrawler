package Url;

import static org.junit.Assert.*;

import org.junit.Test;

public class testvalidateurl
{

	@Test
	public void testValidateUrl()
	{
		//Good Url
		Boolean isValid = Crawler.validateUrl("http://javahungry.blogspot.com/");
		assertEquals(true,isValid);
		
		//Bad Url
		Boolean isValid1 = Crawler.validateUrl("javahungry.blogspot.c");
		assertEquals(false,isValid1);
	}

}
