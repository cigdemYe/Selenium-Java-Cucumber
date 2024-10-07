package pageObjects;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Base_PO {
    private final JavascriptExecutor javascriptExecutor;
    private static final String DOCUMENT_READY_STATE_SCRIPT = "return document.readyState";
    private static final String AJAX_WAIT_SCRIPT = "return typeof jQuery != 'undefined' && jQuery.active != 0";
    private static final Logger logger = LoggerFactory.getLogger(Base_PO.class);


    public Base_PO() {
        PageFactory.initElements(getDriver(), this);
        this.javascriptExecutor = (JavascriptExecutor) getDriver();

    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public void getURL(String url) {
        getDriver().get(url);
    }


    public void sendKeys(WebElement element, String keys) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(keys);
    }


    public void waitUntilClickableAndClick(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public boolean waitVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }

    public static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void safeClick(WebElement element) {
        try {
            if (waitVisible(element))
                element.click();
        } catch (Exception ignored) {
            //noop
        }
    }

    /**
     * ************** SCROLL ***************
     */

    public void scrollIntoView(WebElement element) {
        try {
            waitVisible(element);
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception ignored) {
        }
    }

    public void scrollDown(int i) {
        javascriptExecutor.executeScript("window.scrollBy(0," + i + ")");
    }

    public boolean scrollIfNotVisible(WebElement element) {
        int scrollCount = 0, scrollLimit = 10;
        do {
            try {

                if (isDisplayed(element)) {
                    scrollIntoView(element);
                    break;
                }

                scrollOnePage();
            } catch (Exception e) {
                scrollOnePage();
            }

            scrollCount++;
        }
        while (scrollCount < scrollLimit);

        return element.isDisplayed();
    }

    public void scrollOnePage() {
        try {
            javascriptExecutor.executeScript("window.scrollBy(0, 750)");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public void scrollUp() {
        try {
            javascriptExecutor.executeScript("window.scrollBy(0,-200)");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public void scrollToEndOfPage() {
        for (int i = 0; i < 12; i++) {
            javascriptExecutor.executeScript("window.scrollBy(0, 750)");
            sleep(TimeUnit.SECONDS.toMillis(2));
        }
    }

    public void scrollToTopOfPage() {
        javascriptExecutor.executeScript("window.scrollTo(0, 0)");
        sleep(TimeUnit.SECONDS.toMillis(2));

    }

    /**
     * ************** MOUSE ***************
     */

    public void click(WebElement element) {
        if (scrollIfNotVisible(element)) {
            javascriptExecutor.executeScript("arguments[0].click();", element);
        }
    }


    public void dispatchClickEvent(WebElement element) {
        scrollIfNotVisible(element);
        javascriptExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true}));", element);
    }

    /**
     * ************** KEYBOARD ***************
     */

    public void clear(WebElement element) {
        element.clear();
    }


    /**
     * ************** CONTROLS ***************
     */

    public boolean isClickable(WebElement element) {
        try {
            return !element.getAttribute("class").contains("disabled");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPresent(By by) {
        return getDriver().findElements(by).size() != 0;
    }

    public boolean isDisplayed(WebElement element) {
        try {
            waitUntilVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNotDisplayed(WebElement element) {

        try {
            waitVisible(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled(WebElement element) {
        try {
            waitUntilVisible(element);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSwitched(WebElement element) {

        try {
            waitUntilVisible(element);
            return element.getAttribute("aria-checked").equals("true");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ************** WAIT ***************
     */

    public WebElement waitUntilVisible(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilVisible(WebElement element, int second) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(second))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForAjaxLoad(DriverFactory driverFactory) {
        waitForReadyState(driverFactory);
        sleep(TimeUnit.SECONDS.toMillis(2));
    }

    private static void waitForReadyState(DriverFactory driverFactory) {
        try {
            sleep(250L);
            JavascriptExecutor executor = (JavascriptExecutor) driverFactory.getDriver();
            boolean stillRunningAjax = Boolean.parseBoolean(executor.executeScript(AJAX_WAIT_SCRIPT).toString());
            boolean isDocumentReadyState = executor.executeScript(DOCUMENT_READY_STATE_SCRIPT).equals("complete");

            int i = 0;
            while (!isDocumentReadyState && stillRunningAjax && i < 2) {
                i++;
                sleep(TimeUnit.SECONDS.toMillis(1L));
                stillRunningAjax = Boolean.parseBoolean(executor.executeScript(AJAX_WAIT_SCRIPT).toString());
            }
        } catch (Exception e) {
            System.out.println("WAITER ERROR!!!");
            sleep(1000);
        }

    }

}
