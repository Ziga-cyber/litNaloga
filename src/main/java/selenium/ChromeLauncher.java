package selenium;

import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeLauncher {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "/Users/ziga/LIT_Transit/chromedriver");
		/*
		 * You probably have the Chromes web driver installed on a different location.
		 * Therefore change the "/Users/ziga/LIT_Transit/chromedriver" with the
		 * location of the Chrome drive on your PC.
		 */

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.nba.com/players");

		Scanner scaner = new Scanner(System.in);

		System.out.print("Please write the name of the player: ");
		String playersName = scaner.nextLine();
		scaner.close();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement cookiesDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-banner-sdk")));
		cookiesDiv.findElement(By.id("onetrust-accept-btn-handler")).click();
		// The program accepts the NBA's cookie policies.

		Thread.sleep(6000);
		/*
		 * The program waits for 6 seconds for the website to finish loading itself. /*
		 * Especially problematic are the ads which take quite some time to load.
		 */

		List<WebElement> inputs = driver.findElements(By.className("Input_input__3YQfM"));
		WebElement input = inputs.get(2);
		input.sendKeys(playersName);
		/*
		 * We search for all elements of the class "Input_input__3YQfM". The search
		 * input itself does not have an id, so we have to search by class name. We set
		 * the value of the input to the users input.
		 */

		Thread.sleep(3000);
		/*
		 * After the inputs values is set we wait for a few seconds for the DOM to
		 * update.
		 */

		try {
			WebElement playerLink = driver
					.findElement(By.cssSelector("a.flex.items-center.t6.Anchor_complexLink__2NtkO"));
			playerLink.click();
			// We find the link to the users page and click on it.

		} catch (Exception e) {
			System.out.println("Something went wrong.");
			// There is no player listed in the table after the search.
		}

		Thread.sleep(3000);
		/*
		 * Players page is loaded and the program waits a few seconds for the page to
		 * load.
		 */

		WebElement element = driver.findElement(
				By.cssSelector("a.InnerNavTab_link__sy58U.InnerNavTab_inactive__mUR6Q.Anchor_complexLink__2NtkO"));
		// We find the link to players statistics.

		String link = element.getAttribute("href") + "?SeasonType=Regular%20Season&PerMode=Per40";
		// In order to get players statistic asap. we add
		// "?SeasonType=Regular%20Season&PerMode=Per40" to the link.
		driver.get(link);

		Thread.sleep(5000);
		// The program waits a few seconds for the page to load.

		WebElement table = driver.findElement(By.cssSelector("div.nba-stat-table__overflow"));
		// We find the table with the statistic data.
		List<WebElement> data = table.findElements(By.cssSelector("tr"));
		// We go through all the tables rows.

		int index = 0;
		for (WebElement row : data) {
			String output = "";
			if (index == 0) {
				index++;
				// We skip the first row since, it holds column names.
			} else {
				List<WebElement> td = row.findElements(By.cssSelector("td"));
				// The program finds the rows columns.
				output = output + td.get(0).getText() + " " + td.get(8).getText();
				System.out.println(output);
			}
		}

	}

}
