package logia.zara.application;

import java.io.IOException;
import java.util.List;

import logia.zara.httpclient.listener.SalePriceListener;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestHtmlUnit {

	public static void main(String[] args) throws IOException, InterruptedException {
		try (WebClient _web = new WebClient(BrowserVersion.CHROME)) {
			_web.getOptions().setThrowExceptionOnScriptError(false);
			_web.getOptions().setThrowExceptionOnFailingStatusCode(false);
			_web.getOptions().setJavaScriptEnabled(true);
			_web.getOptions().setCssEnabled(true);
			_web.getOptions().setRedirectEnabled(true);
			_web.getOptions().setDoNotTrackEnabled(true);

			final HtmlPage _page = _web.getPage("http://www.zara.com/us/en/sale/woman/outerwear/view-all/long-trench-coat-c731509p3184997.html");
			List<?> _tags = _page.getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]");
			HtmlDivision _div = (HtmlDivision) _tags.get(0);
			SalePriceListener _salePriceListener = new SalePriceListener();
			_div.addDomChangeListener(_salePriceListener);
			try {
				System.out.println(_salePriceListener.getAvailablePrice());
			}
			catch (InterruptedException __ex) {
				__ex.printStackTrace();
			}
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// List<?> _tags = _page.getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]");
			// HtmlDivision _div = (HtmlDivision) _tags.get(0);
			// SalePriceListener _salePriceListener = new SalePriceListener();
			// _div.addDomChangeListener(_salePriceListener);
			// try {
			// System.out.println(_salePriceListener.getAvailablePrice());
			// }
			// catch (InterruptedException __ex) {
			// __ex.printStackTrace();
			// }
			// }
			// }).start();
			// Thread.sleep(1000000);
		}
		catch (Exception __ex) {
			__ex.printStackTrace();
		}
	}

}
