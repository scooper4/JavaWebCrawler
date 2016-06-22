package Url;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetHtmlTest
{

	@Test
	public void test()
	{
		//Make sure gethtml() returns content from url and is not still null value
		String content = Crawler.getHtml("http://javahungry.blogspot.com/");
		assertNotNull(content);
	}

}
