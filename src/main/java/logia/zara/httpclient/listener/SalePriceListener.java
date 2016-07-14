package logia.zara.httpclient.listener;

import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
import com.gargoylesoftware.htmlunit.html.DomChangeListener;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class SalePriceListener implements DomChangeListener {

	private static final Logger LOGGER           = Logger.getLogger(SalePriceListener.class);
	private static final long   serialVersionUID = 1L;

	private String              price;

	private boolean             onSale           = false;

	public String getAvailablePrice() throws InterruptedException {
		int _n = 0;
		while (this.price == null) {
			if (_n == 200) {
				throw new InterruptedException("Waiting too long!");
			}
			SalePriceListener.LOGGER.debug("Waiting " + _n++);
			Thread.sleep(1000);
		}
		return this.price;
	}

	public boolean isOnSale() {
		return this.onSale;
	}

	@Override
	public void nodeAdded(DomChangeEvent __event) {
		SalePriceListener.LOGGER.debug("Sale listener init");
		List<?> _spans = __event.getChangedNode().getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]/span[1]");
		for (Object name : _spans) {
			HtmlSpan _span = (HtmlSpan) name;
			if (_span.getAttribute("class").equals("sale")) {
				this.price = _span.asText();
			}
			else if (_span.getAttribute("class").equals("line-through")) {
				this.onSale = true;
			}
		}
		SalePriceListener.LOGGER.debug("Have got price");
	}

	@Override
	public void nodeDeleted(DomChangeEvent __event) {
	}
}
