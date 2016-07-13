package logia.zara.httpclient.listener;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
import com.gargoylesoftware.htmlunit.html.DomChangeListener;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public class SalePriceListener implements DomChangeListener {

	private static final Logger	LOGGER				= Logger.getLogger(SalePriceListener.class);
	private static final long	serialVersionUID	= 1L;

	private String				price;

	private boolean				onSale				= false;

	@Override
	public void nodeAdded(DomChangeEvent __event) {
		LOGGER.debug("Sale listener init");
		List<?> _spans = __event.getChangedNode()
		        .getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]/span[1]");
		for (Iterator<?> _iterator = _spans.iterator(); _iterator.hasNext();) {
			HtmlSpan _span = (HtmlSpan) _iterator.next();
			if (_span.getAttribute("class").equals("sale")) {
				this.price = _span.asText();
			}
			else if (_span.getAttribute("class").equals("line-through")) {
				this.onSale = true;
			}
		}
		LOGGER.debug("Have got price");
	}

	@Override
	public void nodeDeleted(DomChangeEvent __event) {
	}

	public String getAvailablePrice() throws InterruptedException {
		int _n = 0;
		while (this.price == null) {
			if (_n == 200) {
				throw new InterruptedException("Waiting too long!");
			}
			LOGGER.debug("Waiting " + _n++);
			Thread.sleep(1000);
		}
		return this.price;
	}

	public boolean isOnSale() {
		return this.onSale;
	}
}
