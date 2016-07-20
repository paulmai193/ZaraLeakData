package logia.zara.application;

import logia.zara.controller.GetUrlController;

/**
 * The Class TestHtmlUnit.
 *
 * @author Paul Mai
 */
public class TestHtmlUnit {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// try (WebClient _web = new WebClient(BrowserVersion.CHROME)) {
		// _web.getOptions().setThrowExceptionOnScriptError(false);
		// _web.getOptions().setThrowExceptionOnFailingStatusCode(false);
		// _web.getOptions().setJavaScriptEnabled(true);
		// _web.getOptions().setCssEnabled(true);
		// _web.getOptions().setRedirectEnabled(true);
		// _web.getOptions().setDoNotTrackEnabled(true);
		//
		// final HtmlPage _page = _web.getPage(
		// "http://www.zara.com/uk/en/sale/woman/outerwear/view-all/long-trench-coat-c731509p3184997.html");
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
		// catch (Exception __ex) {
		// __ex.printStackTrace();
		// }

		GetUrlController _controller = new GetUrlController();
		_controller.exchangeCurrency("EUR", "GBP", (float) 12.99);
	}

}
