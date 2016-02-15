package logia.zara.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JProgressBar;

import logia.utility.httpclient.HttpSendGet;
import logia.zara.model.SaleProductData;
import logia.zara.process.ExportToFile;
import logia.zara.process.ExportToPdf;

import org.apache.log4j.Logger;

/**
 * The Class GetUrlController.
 *
 * @author Paul Mai
 */
public final class GetUrlController {

	/** The Constant MAX. */
	public static final int     MAX         = 99;

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
				for (int i = GetUrlController.MIN; i <= GetUrlController.MAX; i++) {
					if (i < 10) {
						_number = "0" + i;
					}
					else {
						_number = "" + i;
					}
					String _request = __url + _number + ".html";
					HttpSendGet _get = new HttpSendGet(_request, new HashMap<String, String>(), new HashMap<String, String>());
					int _httpCode = _get.execute();
					if (_httpCode == 200) {
						String _html = _get.getResponseContent();

						_html = _html.replaceAll("/>\\s</", "><").replaceAll(">\\s</", "><").replaceAll("\t", "").replaceAll("\r\n", "");

						SaleProductData _data = new SaleProductData();

						List<String> _content1 = this.readProductName(_html);
						if (_content1.size() == 1) {
							_data.setProductName(_content1.get(0));

							List<String> _content2 = this.readProductPrice(_html);
							if (_content2.size() == 1) {
								_data.setProductPrice(_content2.get(0));
							}

							List<String> _content3 = this.readProductSize(_html);
							if (_content3.size() > 0) {
								_data.setProductSizes(_content3);
							}
							else {
								_data.setProductSizes(new ArrayList<String>());
							}

							boolean _content4 = this.readOnSale(_html);
							_data.setOnSale(_content4);

							List<String> _content5 = this.readProductPhoto(_html);
							if (_content5.size() > 0) {
								_data.setPhotoUrl(_content5.get(0));
							}

							_data.setLink(_request);
						}

						// Write to file
						// ExportToFile.exportOnSalesProduct(log, data);
						ExportToFile.exportOnSalesProduct(_pdf, _data);
					}
					else {
						System.out.println("OOPS, http code " + _httpCode);
					}

					this._numProcess++;
					__progressBar.setValue(this._numProcess);

					Thread.sleep(5000);
				}
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

	/**
	 * Read on sale.
	 *
	 * @param __webContent the web content
	 * @return true, if successful
	 */
	private boolean readOnSale(String __webContent) {
		Matcher _matcher = Pattern.compile("span class=\"line-through\"").matcher(__webContent);
		return _matcher.find();
	}

	/**
	 * Read product name.
	 *
	 * @param __webContent the web content
	 * @return the list
	 */
	private List<String> readProductName(String __webContent) {
		Matcher _matcher = Pattern.compile("<div id=\"description\"><h1>(.*?)</h1>").matcher(__webContent);
		List<String> _contents = new ArrayList<String>();
		while (_matcher.find()) {
			String _tmpString = _matcher.group(1).trim();
			_contents.add(_tmpString);
		}
		return _contents;
	}

	/**
	 * Read product price.
	 *
	 * @param __webContent the web content
	 * @return the list
	 */
	private List<String> readProductPrice(String __webContent) {
		Matcher _matcher = Pattern.compile("<span class=\"sale\" data-price=\"(.*?)\">").matcher(__webContent);
		List<String> _contents = new ArrayList<String>();
		while (_matcher.find()) {
			String _tmpString = _matcher.group(1).trim();
			_contents.add("Price: " + _tmpString);
		}
		return _contents;
	}

	/**
	 * Read product size.
	 *
	 * @param __webContent the web content
	 * @return the list
	 */
	private List<String> readProductSize(String __webContent) {
		Matcher _matcher = Pattern.compile("<div class=\"size-select\"><table>(.*?)</table>").matcher(__webContent);
		List<String> _contents = new ArrayList<String>();
		String _size = "";
		while (_matcher.find()) {
			String _tmpString = _matcher.group(1).trim();
			Matcher _trMatcher = Pattern.compile("<tr class=\"product-size gaTrack gaViewEvent\"(.*?)</tr>").matcher(_tmpString);
			while (_trMatcher.find()) {
				String _avaiTr = _trMatcher.group(1).trim();
				Matcher _tdMatcher = Pattern.compile("<td class=\"size-name\">(.*?)</td>").matcher(_avaiTr);
				while (_tdMatcher.find()) {
					String _avaiTd = _tdMatcher.group(1).trim();
					_size += _avaiTd.concat(", ");
				}
			}
			if (_size.length() > 0) {
				_contents.add("Size: " + _size.substring(0, _size.length() - 2));
			}
		}

		return _contents;
	}

	/**
	 * Read product photo URL.
	 *
	 * @param __webContent the __web content
	 * @return the list
	 */
	private List<String> readProductPhoto(String __webContent) {
		String _pattern = "<a href=\"//static.zara.net/photos(.*?)\" class=\"disabled-anchor\">";

		Matcher _matcher = Pattern.compile(_pattern).matcher(__webContent);

		List<String> _contents = new ArrayList<String>();
		while (_matcher.find()) {
			String _tmpString = _matcher.group(1).trim();
			_contents.add("http://static.zara.net/photos" + _tmpString);
		}

		return _contents;
	}

}
