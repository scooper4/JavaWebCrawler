package Url;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class GetPageUrls
{
	
	@After
	public void clearNext(){
		Crawler.next.clear();
	}
	
	@Test
	public void checkIfLinksAreMoreThan50d()
	{
		for(int i = 0 ; i < 49; i++){
			Crawler.next.add("http://javahungry.blogspot.com/");
		}
		int result = Crawler.getPageUrls("http://javahungry.blogspot.com/", 0);
		System.out.println("The size of next is: " + Crawler.next.size());
		assertEquals(0,result);
	}
	
	@Test
	public void checkIfAllLinksAreFound()
	{
		
		int result = Crawler.getPageUrls("http://javahungry.blogspot.com/", 0);
		assertEquals(1,result);
	}
	
	

}
