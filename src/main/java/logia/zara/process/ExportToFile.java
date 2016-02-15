package logia.zara.process;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import logia.zara.model.SaleProductData;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * The Class ExportToFile.
 *
 * @author Paul Mai
 */
public final class ExportToFile {

	private static final Logger LOGGER = Logger.getLogger(ExportToFile.class);

	/**
	 * Export on sales product.
	 *
	 * @param __output the __output
	 * @param __data the __data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void exportOnSalesProduct(File __output, SaleProductData __data) throws IOException {
		if (__data.isOnSale() && !__data.getProductSizes().isEmpty()) {
			List<String> lines = new ArrayList<String>();

			lines.add(__data.getProductName());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			lines.add(__data.getProductPrice());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			FileUtils.writeLines(__output, __data.getProductSizes(), true);

			lines.add(__data.getLink());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			lines.add("================================================");
			FileUtils.writeLines(__output, lines, true);
			lines.clear();
		}
	}

	/**
	 * Export on sales product.
	 *
	 * @param __pdf the __pdf
	 * @param __data the __data
	 * @throws Exception the exception
	 */
	public static void exportOnSalesProduct(ExportToPdf __pdf, SaleProductData __data) throws Exception {
		if (__data.isOnSale() && !__data.getProductSizes().isEmpty()) {
			__pdf.addParagraph(__data.getProductName());
			__pdf.addParagraph(__data.getProductPrice());
			__pdf.addParagraph(__data.getProductSizesToString());
			try {
				System.out.println(__data.getPhotoUrl());
				__pdf.addImage(new URL(__data.getPhotoUrl()));
			}
			catch (IOException _e) {
				LOGGER.error("URL error: " + __data.getPhotoUrl(), _e);
				throw _e;
			}
			__pdf.addParagraph(__data.getLink());
			__pdf.addParagraph("================================================");
		}
	}
}
