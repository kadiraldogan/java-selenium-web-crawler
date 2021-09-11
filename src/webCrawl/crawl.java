package webCrawl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class crawl {

    public static String tendNum=null, quality=null, placeWork=null, tendType=null;
    public static int count=1;
    public static void main (String[] args) throws InterruptedException {
        List<WebElement> searchPageResults;
        List<String> allLink = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "C:/Users/kadir/FrameWork/chromeDriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window();
        driver.get("https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari?txv=9&currentPage=1");
        for(int i = 1; i <= 20; i++) {
            driver.get("https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari?txv=9&currentPage=" + i);
            Thread.sleep(2000);
            searchPageResults=driver.findElements(By.xpath("//div[@class='col']/igt-ad-single-list/ng-component/a"));
            for(WebElement link: searchPageResults) {
                allLink.add(link.getAttribute("href"));
            }
        }
        for(String eachLinkText : allLink)  {
            System.out.println(eachLinkText);
            getLink(eachLinkText);
            setTxt(tendNum,quality,placeWork,tendType);
        }
        driver.quit();
    }
    public static void getLink (String url) throws InterruptedException {
        List<WebElement> searchPageResults;
        List<String> allTitle = new ArrayList<>();
        List<String> allDesc = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "C:/Users/kadir/Selenium/chromeDriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window();
        driver.get(url);
        Thread.sleep(1000);
        searchPageResults = driver.findElements(By.xpath("//*[@class='single-ilan-list']/ul/li/*[@class='list-title']"));
        for(WebElement link: searchPageResults) {
            allTitle.add(link.getText());
        }
        searchPageResults = driver.findElements(By.xpath("//*[@class='single-ilan-list']/ul/li/*[@class='list-desc']"));
        for(WebElement link: searchPageResults) {
            allDesc.add(link.getText());
        }
        for(int i=0; i<allTitle.size(); i++)   {
            if(allTitle.get(i).equals("İhale Kayıt No"))
                tendNum=allDesc.get(i);
            else if(allTitle.get(i).equals("Niteliği, Türü ve Miktarı"))
                quality=allDesc.get(i);
            else if(allTitle.get(i).equals("İşin Yapılacağı Yer"))
                placeWork=allDesc.get(i);
            else if(allTitle.get(i).equals("İhale Türü"))
                tendType=allDesc.get(i);
        }
        driver.quit();
    }
    public static void setTxt (String wrTendNum, String wrQuality, String wrPlaceWork, String wrTendType)    {
        File file = new File("output//data.txt");
        try {
            if(!file.exists())
                file.createNewFile();
            String value = count + ". İlan)  " + wrTendNum + "  |  " + wrQuality + "  |  " + wrPlaceWork + "  |  " + wrTendType;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(value);
            writer.newLine();
            writer.close();
            count++;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tendNum=null;
        quality=null;
        placeWork=null;
        tendType=null;
    }
}
