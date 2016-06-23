package Url;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConcatTest
{

	@Test
	public void concatStringsWithoutHttp()
	{
		Crawler.initialUrl = "red";
		String result = Crawler.concatLink("apple");
		assertEquals("redapple",result);
	}
	

	@Test
	public void concatStringsWithtHttp()
	{
		Crawler.initialUrl = "red";
		String result = Crawler.concatLink("http:apple");
		String result1 = Crawler.concatLink("https:apple");
		assertEquals("with http: ","http:apple",result);
		assertEquals("with https: ", "https:apple",result1);
	}


}
