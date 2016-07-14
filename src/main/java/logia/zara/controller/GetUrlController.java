package logia.zara.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JProgressBar;

import logia.utility.json.JsonUtil;
import logia.zara.application.Application;
import logia.zara.httpclient.HttpUnitRequest;
import logia.zara.httpclient.listener.SalePriceListener;
import logia.zara.model.SaleProductData;
import logia.zara.process.ExportToFile;
import logia.zara.process.ExportToPdf;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * The Class GetUrlController.
 *
 * @author Paul Mai
 */
public final class GetUrlController {

	/** The Constant MAX. */
	public static final int     MAX         = 10;

	/** The Constant MIN. */
	public static final int     MIN         = 1;

	/** The Constant LOGGER. */
	private static final Logger LOGGER      = Logger.getLogger(GetUrlController.class);

	/** The _num process. */
	private int                 _numProcess = 0;

	/**
	 * Scan url.
	 *
	 * @param __url the url
	 * @param __output the output file or folder
	 * @param __progressBar the progress bar
	 */
	public synchronized void scanUrl(String __url, String __output, JProgressBar __progressBar) {
		try {
			__progressBar.setString("Scanning link, please do not turn off application!");
			__url = __url.substring(0, __url.lastIndexOf(".html") - 2);
			File _log = new File(__output);
			if (_log.isDirectory()) {
				_log = new File(__output + File.separator + "products (" + new SimpleDateFormat("dd MMM, yyyy").format(new Date()) + ").pdf");
			}
			String _number;
			try (ExportToPdf _pdf = new ExportToPdf(_log)) {
				_pdf.addParagraph("Sale Product");
				_pdf.addParagraph("\n");

				final List<SaleProductData> _productDatas = new ArrayList<>();

				for (int i = GetUrlController.MIN; i <= GetUrlController.MAX; i++) {
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

						// Write to file
						ExportToFile.exportOnSalesProduct(_pdf, _data);
					}
					catch (InterruptedException __ex) {
						GetUrlController.LOGGER.warn(__ex.getMessage() + " Go to next link!");
						continue;
					}
					catch (Exception __ex) {
						GetUrlController.LOGGER.error("Url " + _request + " not suitable", __ex);
					}

					this._numProcess++;
					__progressBar.setValue(this._numProcess);

					Thread.sleep(5000);
				}
				// Save data to Json DB
				FileUtils.write(Application.DB, JsonUtil.toJsonArray(_productDatas.toArray()).toString(), false);
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

	public SaleProductData scanUrl(String __url) throws Exception {
		SaleProductData _data = new SaleProductData();

		_data.setLink(__url);

		// NEW
		try (HttpUnitRequest _httpUnitRequest = new HttpUnitRequest(__url);) {
			HtmlPage _page = _httpUnitRequest.crawl();

			// Get price and on sale status
			GetUrlController.LOGGER.debug("Scan link " + __url);
			GetUrlController.LOGGER.debug("Start get price");
			List<?> _tags = _page.getByXPath("//*[@id=\"product\"]/div[2]/div/div/div[1]");
			HtmlDivision _div = (HtmlDivision) _tags.get(0);
			SalePriceListener _salePriceListener = new SalePriceListener();
			_div.addDomChangeListener(_salePriceListener);
			_data.getProductData().setProductPrice(_salePriceListener.getAvailablePrice());

			_data.getProductData().setOnSale(_salePriceListener.isOnSale());
			GetUrlController.LOGGER.debug("End get price");

			// Get name
			GetUrlController.LOGGER.debug("Start get name");
			_tags = _page.getByXPath("//*[@id=\"product\"]/div[2]/div/div/header/h2/text()");
			_data.getProductData().setProductName(((DomText) _tags.get(0)).asText());
			GetUrlController.LOGGER.debug("End get name");

			// Get photo url
			GetUrlController.LOGGER.debug("Start get photo");
			_tags = _page.getByXPath("//*[@id=\"main-images\"]/div[1]/a");
			HtmlAnchor _a = (HtmlAnchor) _tags.get(0);
			_data.getProductData().setPhotoUrl("http:" + _a.getHrefAttribute());
			GetUrlController.LOGGER.debug("End get photo");

			// Get product size
			GetUrlController.LOGGER.debug("Start get size");
			List<String> _sizes = new ArrayList<>();
			_tags = _page.getByXPath("//*[@id=\"product\"]/div[2]/div/div/form/div[1]/div/table/tbody");
			HtmlTableBody _tBody = (HtmlTableBody) _tags.get(0);
			List<HtmlTableRow> _rows = _tBody.getRows();
			for (HtmlTableRow _tr : _rows) {
				if (_tr.getAttribute("class").equals("product-size _product-size ")) {
					_sizes.add(_tr.asText());
				}
			}
			GetUrlController.LOGGER.debug("End get size");

			return _data;
		}
		catch (Exception __ex) {
			throw __ex;
		}

	}

}
