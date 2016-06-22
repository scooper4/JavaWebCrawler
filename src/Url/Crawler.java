package Url;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler
{
	private static HashSet<String> links = new HashSet<String>();
	private static ArrayList<String> next = new ArrayList<String>();
	public static String initialUrl;
	public static void main(String[] args)
	
	{
		// TODO Auto-generated method stub
		initialUrl = getInput();	
		validateUrl(initialUrl);	
		next.add(initialUrl); //add initial url to links visited
		if (validateUrl(initialUrl)){
			System.out.println("Validated Url");
			getPageUrls(initialUrl,0);	
		}else{
		//else still try debug
			System.out.println("Validate Url FAILED");	
		}
		printLinks(); //printlinks
		
	}
	
	
	public static String getInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("What is the Url you would like to crawl");
		String input = sc.nextLine();
		sc.close();
		return input;
	}
	
	public static Boolean validateUrl(String url){
		
		try {
		      URL newUrl = new URL(url);
		      return true;
		    }
		    catch (MalformedURLException e) {
//		    	e.printStackTrace();
		        return false;
		    }
	}
	
	public static String getHtml(String url) {
		String content = null;
		URLConnection connection = null;
		try{
			connection = new URL(url).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");//403 error  Server returned HTTP response code: 403 for URL
			Scanner sc = new Scanner(connection.getInputStream());
			sc.useDelimiter("\\Z");
			content = sc.next();
			sc.close();
			
		}catch( Exception e){
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static int getPageUrls(String url, int i){
//		System.out.println("i is now : " + i + " url is :" + url);
		String html = getHtml( url);
		Pattern p = Pattern.compile("href=\"([^\"]*)\"");
		Matcher m = p.matcher(html);
		while (m.find()){
			url = m.group().substring(6, m.group().length() -1); //remove href and ending "
			if (url.contains(".css") || url.contains("#") || url.length() == 1 || url.contains("javascript:")){
				continue;
			}
			if (links.add(url) == true){
				next.add(concatLink(url)); //add to visted links and concat if needed
			}
			//check to see if links is equal to 50 if so exit loop
			if (links.size() == 49){ 
				return 0;
			}
			else if(next.size()  == (i+1)){
				System.out.println("No more Links to crawl");
				return 1;
			}			
		}
		

		getPageUrls(next.get(++i), i);
		return 0;
	}
	
	public static String concatLink (String link ){
		if(link.contains("http:") || link.contains("https:")){
			return link;
		}
		link = initialUrl + link;
		return link;
	}
	
	public static void printLinks(){
		ListIterator<String> links = next.listIterator();
		while (links.hasNext()){
			System.out.println(links.next());
		}
	}

}
