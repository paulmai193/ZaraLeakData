package logia.zara.httpclient.listener;

import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomChangeEvent;
import com.gargoylesoftware.htmlunit.html.DomChangeListener;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * The listener interface for receiving salePrice events.
 * The class that is interested in processing a salePrice
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSalePriceListener<code> method. When
 * the salePrice event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SalePriceEvent
 */
public class SalePriceListener implements DomChangeListener {

	/** The Constant LOGGER. */
	private static final Logger	LOGGER				= Logger.getLogger(SalePriceListener.class);

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/** The on sale. */
	private boolean				onSale				= false;

	/** The price. */
	private String				price;

	/**
	 * Gets the available price.
	 *
	 * @return the available price
	 * @throws InterruptedException the interrupted exception
	 */
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

	/**
	 * Checks if is on sale.
	 *
	 * @return true, if is on sale
	 */
	public boolean isOnSale() {
		return this.onSale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gargoylesoftware.htmlunit.html.DomChangeListener#nodeAdded(com.gargoylesoftware.htmlunit.
	 * html.DomChangeEvent)
	 */
	@Override
	public void nodeAdded(DomChangeEvent __event) {
		SalePriceListener.LOGGER.debug("Sale listener init");
		List<?> _spans = __event.getChangedNode()
		        .getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]/span[1]");
		for (Object name : _spans) {
			HtmlSpan _span = (HtmlSpan) name;
			if (_span.getAttribute("class").equals("sale")) {
				this.price = _span.asText();
			}
			else if (_span.getAttribute("class").equals("line-through")) {
				this.onSale = true;
			}
		}
		_spans = __event.getChangedNode()
		        .getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]/span[2]");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.DomChangeListener#nodeDeleted(com.gargoylesoftware.
	 * htmlunit.html.DomChangeEvent)
	 */
	@Override
	public void nodeDeleted(DomChangeEvent __event) {
	}
}
