package Url;

import static org.junit.Assert.*;

import org.junit.Test;

public class testvalidateurl
{

	@Test
	public void goodUrlReturnsTrue()
	{
		//Good Url
		Boolean isValid = Crawler.validateUrl("http://javahungry.blogspot.com/");
		assertEquals(true,isValid);	

	}
	@Test
	public void badUrlReturnsFalse()
	{	
		//Bad Url
		Boolean isValid = Crawler.validateUrl("javahungry.blogspot.c");
		assertEquals(false,isValid);
	}

}
