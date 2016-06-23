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
	static HashSet<String> links = new HashSet<String>();
	static ArrayList<String> next = new ArrayList<String>();
	static String initialUrl;
	
	public static void main(String[] args){
	
		main();
		
	}
	
	/**
	 * Takes in input from Scanner and returns the String
	 *
	 * @param  		none
	 * @return      the input from scanner a String
	 */
	public static String getInput(){
		Scanner sc = new Scanner(System.in);
		System.out.println("What is the Url you would like to crawl");
		String input = sc.nextLine();
		sc.close();
		return input;
	}
	
	/**
	 * Checks to see if the input url is a valid url 
	 * If an Url object is able to be created with the url paramter return true
	 * If not return false 
	 *
	 * @param  url  A Website Url String
	 * @return      true if the url is a valid url, false if the url is not valid
	 */
	public static Boolean validateUrl(String url){
		
		try {
		      URL newUrl = new URL(url);
		      return true;
		    }
		    catch (MalformedURLException e) {
		        return false;
		    }
	}
	
	/**
	 * Take in a url and trys to open a new connection on the URL object,
	 * use  scanner to return the content to a String object.
	 * If it fails it returns a null object
	 * 
	 * @param  url  the url for the website you want to get html from
	 * @return      returns html content in string format
	 */
	
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
	/**
	 * Takes in a String url and an int i for parameters.
	 * gets the string html from the getHtml() using the url method.
	 * Sets a pattern in matcher to use regex to find all strings beginning with href 
	 * all the way to the ". Uses a while loop to find all matches and once it does
	 * it strips everything except for the url. To get rid off urls we dont want we
	 * have an if statement that checks if the new url contains ".css" || "#" || "javascript:"
	 * || or is only of length 1 and bypasses this iteration. 
	 * <p>
	 * We then try to add the url to the Hash set if it returns true then
	 * we know it is not a duplicate, and we also add it to the array list.
	 * To make sure we have proper links that the Crawler can Crawl we call
	 * the concatLink() method on the url.
	 * This method always returns immediately, whether or not the 
	 * image exists. When this applet attempts to draw the image on
	 * the screen, the data will be loaded. The graphics primitives 
	 * that draw the image will incrementally paint on the screen. 
	 * <p>
	 * We check to see during each iteration if the array list has 50 links or if
	 * the size of the Arraylist is == to the next index of the url to be Crawled,
	 * which means there are no more links to crawl on the site.
	 * <p>
	 * Using recursion this method calls itself and the array list, pre- increments the index ,to traverse all the
	 * links that that are in the list until one of the two base cases are met.
	 * 
	 * 
	 * @param  url  The String url to the page we want to get Html from
	 * @param  i	The location in the array list we want to Crawl, an int
	 * @return      returns 0 there are 50 links in the list, return 1 if there are no more links to crawl
	 */
	public static int getPageUrls(String url, int i){
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
	/**
	 * Takes in a String named link if it contains http: or https:
	 * return the original link. If not concatenate the initialUrl
	 * with the link and return the Link 
	 * 
	 * This method is to deal with relative links so the crawler
	 * can know the proper url to crawl.
	 *
	 * @param  link  a link generated by the Regex from the getPageUrl()
	 * @return      a link of type String
	 */
	public static String concatLink (String link ){
		if(link.contains("http:") || link.contains("https:")){
			return link;
		}
		link = initialUrl + link;
		return link;
	}
	
	/**
	 * Creates a list iterator object from next ArrayList 
	 * Uses a while loop to print out all links in the list
	 *
	 * @param  		none
	 * @return      void
	 */
	public static void printLinks(){
		ListIterator<String> links = next.listIterator();
		while (links.hasNext()){
			System.out.println(links.next());
		}
	}
	
	/**
	 *  Gets intitalUrl to crawl from scanner object and inputs in validateUrl as a paramter
	 *  To check if the url is Valid. If not it prints error message and returns -1.
	 * If successful it adds the initialUrl to the next Array list and runs getPageUrls()
	 * with the intialUrl and 0 the start of the ArrayList to crawl.
	 * <p>
	 * This method is the main method to run the crawler, It prints all the links
	 * found or stops once 50 links are found by the crawler.
	 *
	 * @param  		none
	 * @return      an int 3 if successful and -1 is url can not be validated
	 */
	
	public static int main(){
		initialUrl = getInput();		
		if (validateUrl(initialUrl)){
			System.out.println("Validated Url");
			next.add(initialUrl); //add initial url to links visited
			getPageUrls(initialUrl,0);	
		}else{
		//else still try debug
			System.out.println("Validate Url FAILED");
			return -1;
		}
		printLinks(); //printlinks
		return 3;
	}

}
