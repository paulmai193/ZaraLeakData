package logia.zara.controller;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JProgressBar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import logia.zara.db.Data;
import logia.zara.db.DataDAO;
import logia.zara.httpclient.HttpUnitRequest;
import logia.zara.httpclient.listener.SalePriceListener;
import logia.zara.model.SaleProductData;
import logia.zara.process.ExportToFile;
import logia.zara.process.ExportToPdf;
import logia.zara.process.ScanUrlProcess;

/**
 * The Class GetUrlController.
 *
 * @author Paul Mai
 */
public final class GetUrlController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GetUrlController.class);

	/**
	 * Exchange currency, using Yahoo API.
	 *
	 * @param __from the from
	 * @param __to the to
	 * @param __value the value
	 * @return the exchaged value
	 * @throws Exception the exception
	 */
	public float exchangeCurrency(String __from, String __to, float __value) throws Exception {
		if (__from.equals(__to)) {
			return __value;
		}
		String _api = "http://api.fixer.io/latest?base={0}&symbols={1}";
		_api = MessageFormat.format(_api, __from, __to);
		try (HttpUnitRequest _httpUnitRequest = new HttpUnitRequest(_api);) {
			JsonObject _jsonApiResponse = (JsonObject) new JsonParser()
			        .parse(_httpUnitRequest.rawCrawl());
			JsonObject _jsonRates = _jsonApiResponse.get("rates").getAsJsonObject();
			float _rate = _jsonRates.get(__to).getAsFloat();
			return _rate * __value;
		}
		catch (Exception __ex) {
			throw __ex;
		}
	}

	/**
	 * Scan url.
	 *
	 * @param __url the url
	 * @return the sale product data
	 * @throws Exception the exception
	 */
	public SaleProductData scanUrl(String __url) throws Exception {
		SaleProductData _data = new SaleProductData();
		_data.getProductData().setLink(__url);

		HtmlPage _page = null;
		try (HttpUnitRequest _httpUnitRequest = new HttpUnitRequest(__url);) {
			_page = _httpUnitRequest.crawl();

			// Get price and on sale status
			GetUrlController.LOGGER.debug("Scan link " + __url);
			GetUrlController.LOGGER.debug("Start get price");
			List<?> _tags = _page.getByXPath("//*[@id=\"product\"]/div[3]/div/div/div");
			if (_tags.size() == 0) {
				throw new InterruptedException("No price in this link");
			}
			HtmlDivision _div = (HtmlDivision) _tags.get(0);
			SalePriceListener _salePriceListener = new SalePriceListener();
			_div.addDomChangeListener(_salePriceListener);
			String _availablePrice = _salePriceListener.getAvailablePrice();
			float _productPrice;
			String _currency;
			try {
				// _data.getProductData().setProductPrice(_availablePrice.split(" ")[0]);
				// _data.getProductData().setCurrency(_availablePrice.split(" ")[1]);
				String _rawProductPrice = _availablePrice.split(" ")[0];
				_productPrice = Float.parseFloat(_rawProductPrice);
				_currency = _availablePrice.split(" ")[1];
			}
			catch (NumberFormatException __ex) {
				String _rawProductPrice = _availablePrice.split(" ")[1];
				_productPrice = Float.parseFloat(_rawProductPrice);
				_currency = _availablePrice.split(" ")[0];
			}
			if (_currency.equals("¥")) {
				// Special, convert ¥ to CNY
				_currency = "CNY";
			}
			float _exchangedPrice = this.exchangeCurrency(_currency, "USD", _productPrice);
			_data.getProductData().setProductPrice(_exchangedPrice);
			_data.getProductData().setCurrency("USD");

			_data.getProductData().setOnSale(_salePriceListener.isOnSale());
			GetUrlController.LOGGER.debug("End get price");

			// Get ref
			GetUrlController.LOGGER.debug("Start get ref");
			_tags = _page.getByXPath("//*[@id=\"product\"]/div[3]/div/div/p[2]");
			if (_tags.size() == 0) {
				throw new InterruptedException("No ref code in this link");
			}
			_data.setRef(((HtmlParagraph) _tags.get(0)).asText());

			// Get name
			GetUrlController.LOGGER.debug("Start get name");
			_tags = _page.getByXPath("//*[@id=\"product\"]/div[3]/div/div/header/h1/text()");
			_data.getProductData().setProductName(((DomText) _tags.get(0)).asText());
			GetUrlController.LOGGER.debug("End get name");

			// Get photo url
			GetUrlController.LOGGER.debug("Start get photo");
			_tags = _page.getByXPath("//*[@id=\"main-images\"]/div[1]/a/img");
			HtmlImage _img = (HtmlImage) _tags.get(0);
			_data.getProductData().setPhotoUrl("http:" + _img.getSrcAttribute());
			GetUrlController.LOGGER.debug("End get photo");

			// Get product size
			GetUrlController.LOGGER.debug("Start get size");
			List<String> _sizes = new ArrayList<>();
			_tags = _page
			        .getByXPath("//*[@id=\"product\"]/div[3]/div/div/form/div[1]/div/table/tbody");
			HtmlTableBody _tBody = (HtmlTableBody) _tags.get(0);
			List<HtmlTableRow> _rows = _tBody.getRows();
			for (HtmlTableRow _tr : _rows) {
				if (_tr.getAttribute("class").equals("product-size _product-size ")) {
					for (HtmlTableCell _td : _tr.getCells()) {
						if (_td.getAttribute("class").equals("size-name _size-name")) {
							_sizes.add(_td.asText());
						}
					}
				}
			}
			_data.getProductData().setProductSizes(_sizes);
			GetUrlController.LOGGER.debug("End get size");

			return _data;
		}
		catch (Exception __ex) {
			throw __ex;
		}
		finally {
			if (_page != null) {
				FileUtils.writeStringToFile(new File("/home/logia193/Desktop/testpage.htm"),
				        _page.asXml());
			}
		}
	}

	/**
	 * Scan url.
	 *
	 * @param __url the url
	 * @param __output the output file or folder
	 * @param __progressBar the progress bar
	 */
	public void scanUrl(String __url, String __output, JProgressBar __progressBar) {
		try {
			__progressBar.setString("Scanning link, please do not turn off application!");
			__url = __url.substring(0, __url.lastIndexOf(".html") - 2);
			File _log = new File(__output);
			if (_log.isDirectory()) {
				_log = new File(__output + File.separator + "products ("
				        + new SimpleDateFormat("dd MMM, yyyy").format(new Date()) + ").pdf");
			}
			String _number;
			try (ExportToPdf _pdf = new ExportToPdf(_log)) {
				_pdf.addParagraph("Sale Product");
				_pdf.addParagraph("\n");

				final List<SaleProductData> _productDatas = new ArrayList<>();

				for (int i = ScanUrlProcess.MIN; i <= ScanUrlProcess.MAX; i++) {
					if (i < 10) {
						_number = "0" + i;
					}
					else {
						_number = "" + i;
					}
					final String _request = __url + _number + ".html";
					try {
						SaleProductData _data = this.scanUrl(_request);

						// Add to products list
						_productDatas.add(_data);

						// Save data to Json DB
						DataDAO.getInstance().set(new Data(_data));
					}
					catch (InterruptedException __ex) {
						GetUrlController.LOGGER.warn(__ex.getMessage() + " Go to next link!");
						continue;
					}
					catch (Exception __ex) {
						GetUrlController.LOGGER.error("Url " + _request + " not suitable", __ex);
					}

					ScanUrlProcess.numProcess++;
					__progressBar.setValue(ScanUrlProcess.numProcess);

					Thread.sleep(5000);
				}

				// Write to file
				ExportToFile.exportOnSalesProduct(_pdf, _productDatas);

			}
			catch (Exception _e) {
				throw _e;
			}

		}
		catch (Exception _e) {
			GetUrlController.LOGGER.error("Error when reading data from url", _e);
		}
		finally {
			__progressBar.setString("Finish!");
			__progressBar.setValue(0);
		}
	}

}
