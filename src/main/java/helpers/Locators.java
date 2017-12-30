package helpers;

import org.openqa.selenium.By;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Locators {
    private static Properties locators;
    static HashMap<String, String> lookup = new HashMap<String, String>();

    private enum  LocatorType{
        id, name, xpath, tag, css, text
    }

    static{
        locators = new Properties();
        InputStream is = Locators.class.getResourceAsStream("/locators.properties");
        try {
            locators.load(is);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static By get(String locatorName){
        String propertyValue = locators.getProperty(locatorName);
        By locator = getLocator(propertyValue);
        String locatorValue = propertyValue.replaceAll("\\w{2,8}=","");
        lookup.put(locatorValue, locatorName);
        return locator;
    }

    private static By getLocator(String locator){
        String[] locatorItems = locator.split("=",2);
        LocatorType locatorType = LocatorType.valueOf(locatorItems[0]);

        switch(locatorType) {

            case id :{
                return By.id(locatorItems[1]);
            }

            case name:{
                return By.name(locatorItems[1]);
            }

            case css:{
                return By.cssSelector(locatorItems[1]);
            }

            case xpath:{
                return By.xpath(locatorItems[1]);
            }

            case tag:{
                return By.tagName(locatorItems[1]);
            }

            case text:{
                return By.linkText(locatorItems[1]);
            }


            default:{
                throw new IllegalArgumentException("No such locator type: " + locatorItems[0]);
            }
        }
    }
}
