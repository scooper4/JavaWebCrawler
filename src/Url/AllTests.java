package Url;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testGetInput.class, testvalidateurl.class, GetHtmlTest.class, ConcatTest.class, GetPageUrls.class })
public class AllTests
{

}
