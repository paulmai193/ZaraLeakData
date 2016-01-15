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
import logia.zara.model.ExportToFile;
import logia.zara.model.SaleProductData;

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
	 * @param url the url
	 * @param output the output file or folder
	 * @param progressBar the progress bar
	 */
	public synchronized void scanUrl(String url, String output, JProgressBar progressBar) {
		try {
			progressBar.setString("Scanning link, please do not turn off application!");
			url = url.substring(0, url.lastIndexOf(".html") - 2);
			File log = new File(output);
			if (log.isDirectory()) {
				log = new File(output + File.separator + "products (" + new SimpleDateFormat("dd MMM, yyyy").format(new Date()) + ").txt");
			}
			String number;
			for (int i = GetUrlController.MIN; i <= GetUrlController.MAX; i++) {
				if (i < 10) {
					number = "0" + i;
				}
				else {
					number = "" + i;
				}
				String request = url + number + ".html";
				HttpSendGet get = new HttpSendGet(request, new HashMap<String, String>(), new HashMap<String, String>());
				int httpCode = get.execute();
				if (httpCode == 200) {
					String html = get.getResponseContent();

					html = html.replaceAll("/>\\s</", "><").replaceAll(">\\s</", "><").replaceAll("\t", "").replaceAll("\r\n", "");

					SaleProductData data = new SaleProductData();

					List<String> content1 = this.readProductName(html);
					if (content1.size() == 1) {
						data.setProductName(content1.get(0));

						List<String> content2 = this.readProductPrice(html);
						if (content2.size() == 1) {
							data.setProductPrice(content2.get(0));
						}

						List<String> content3 = this.readProductSize(html);
						if (content3.size() > 0) {
							data.setProductSizes(content3);
						}

						boolean content4 = this.readOnSale(html);
						data.setOnSale(content4);

						data.setLink(request);
					}

					// Write to file
					ExportToFile.exportOnSalesProduct(log, data);
				}
				else {
					System.out.println("OOPS, http code " + httpCode);
				}

				this._numProcess++;
				progressBar.setValue(this._numProcess);

				Thread.sleep(5000);
			}

		}
		catch (Exception e) {
			GetUrlController.LOGGER.error("Error when reading data from url", e);
		}
		finally {
			progressBar.setString("Finish!");
			progressBar.setValue(0);
		}
	}

	/**
	 * Read on sale.
	 *
	 * @param webContent the web content
	 * @return true, if successful
	 */
	private boolean readOnSale(String webContent) {
		Matcher matcher = Pattern.compile("span class=\"line-through\"").matcher(webContent);
		return matcher.find();
	}

	/**
	 * Read product name.
	 *
	 * @param webContent the web content
	 * @return the list
	 */
	private List<String> readProductName(String webContent) {
		Matcher matcher = Pattern.compile("<div id=\"description\"><h1>(.*?)</h1>").matcher(webContent);
		List<String> contents = new ArrayList<String>();
		while (matcher.find()) {
			String tmpString = matcher.group(1).trim();
			contents.add(tmpString);
		}
		return contents;
	}

	/**
	 * Read product price.
	 *
	 * @param webContent the web content
	 * @return the list
	 */
	private List<String> readProductPrice(String webContent) {
		Matcher matcher = Pattern.compile("<span class=\"sale\" data-price=\"(.*?)\">").matcher(webContent);
		List<String> contents = new ArrayList<String>();
		while (matcher.find()) {
			String tmpString = matcher.group(1).trim();
			contents.add("Price: " + tmpString);
		}
		return contents;
	}

	/**
	 * Read product size.
	 *
	 * @param webContent the web content
	 * @return the list
	 */
	private List<String> readProductSize(String webContent) {
		Matcher matcher = Pattern.compile("<div class=\"size-select\"><table>(.*?)</table>").matcher(webContent);
		List<String> contents = new ArrayList<String>();
		String size = "";
		while (matcher.find()) {
			String tmpString = matcher.group(1).trim();
			Matcher trMatcher = Pattern.compile("<tr class=\"product-size gaTrack gaViewEvent\"(.*?)</tr>").matcher(tmpString);
			while (trMatcher.find()) {
				String avaiTr = trMatcher.group(1).trim();
				Matcher tdMatcher = Pattern.compile("<td class=\"size-name\">(.*?)</td>").matcher(avaiTr);
				while (tdMatcher.find()) {
					String avaiTd = tdMatcher.group(1).trim();
					size += avaiTd.concat(", ");
				}
			}
			if (size.length() > 0) {
				contents.add("Size: " + size.substring(0, size.length() - 2));
			}
		}

		return contents;
	}

}
