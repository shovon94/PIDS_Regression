package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class WindowHandler {

	WebDriver driver;
	String parent_window;
	String child_window;
	Robot robot;
	
	public WindowHandler(WebDriver driver) throws AWTException {
		this.driver = driver;
		parent_window = driver.getWindowHandle();
		robot = new Robot();
	}
	
	
	public void switchToChild() throws InterruptedException {
		

		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();

		while (i1.hasNext()) {
			child_window = i1.next();
			if (!parent_window.equalsIgnoreCase(child_window)) {
				driver.switchTo().window(child_window);
				Thread.sleep(2000);
			}
		}
	}
	
	public void closeChild(){
		

		driver.switchTo().window(child_window).close();
	}

	public void switchToParent() {
		driver.switchTo().window(parent_window);
	}
	
	public void zoomOut(int size){
		for (int i = 0; i < size; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	public void zoomIn(int size){
		for (int i = 0; i < size; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

}
