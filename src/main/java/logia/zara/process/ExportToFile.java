package logia.zara.process;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import logia.zara.model.SaleProductData;

/**
 * The Class ExportToFile.
 *
 * @author Paul Mai
 */
public final class ExportToFile {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ExportToFile.class);

	/**
	 * Export on sales product.
	 *
	 * @param __pdf the pdf
	 * @param __datas the datas
	 * @throws Exception the exception
	 */
	public static void exportOnSalesProduct(final ExportToPdf __pdf, List<SaleProductData> __datas)
	        throws Exception {
		__datas.forEach(new Consumer<SaleProductData>() {

			@Override
			public void accept(SaleProductData __t) {
				try {
					ExportToFile.exportOnSalesProduct(__pdf, __t);
				}
				catch (Exception __ex) {
					// Swallow this exception
				}
			}
		});
	}

	/**
	 * Export on sales product.
	 *
	 * @param __pdf the __pdf
	 * @param __data the __data
	 * @throws Exception the exception
	 */
	public static void exportOnSalesProduct(ExportToPdf __pdf, SaleProductData __data)
	        throws Exception {
		if (__data.getProductData().isOnSale()
		        && !__data.getProductData().getProductSizes().isEmpty()) {
			__pdf.addParagraph(__data.getProductData().getProductName());
			__pdf.addParagraph(__data.getProductData().getProductPrice() + " "
			        + __data.getProductData().getCurrency());
			__pdf.addParagraph(__data.getProductData().getProductSizesToString());
			try {
				__pdf.addImage(new URL(__data.getProductData().getPhotoUrl()));
			}
			catch (IOException _e) {
				ExportToFile.LOGGER.error("URL error: " + __data.getProductData().getPhotoUrl(),
				        _e);
				throw _e;
			}
			__pdf.addParagraph(__data.getProductData().getLink());
			__pdf.addParagraph("================================================");
		}
	}

	/**
	 * Export on sales product.
	 *
	 * @param __output the __output
	 * @param __data the __data
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void exportOnSalesProduct(File __output, SaleProductData __data)
	        throws IOException {
		if (__data.getProductData().isOnSale()
		        && !__data.getProductData().getProductSizes().isEmpty()) {
			List<String> lines = new ArrayList<String>();

			lines.add(__data.getProductData().getProductName());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			lines.add(__data.getProductData().getProductPrice() + " "
			        + __data.getProductData().getCurrency());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			FileUtils.writeLines(__output, __data.getProductData().getProductSizes(), true);

			lines.add(__data.getRef());
			FileUtils.writeLines(__output, lines, true);
			lines.clear();

			lines.add("================================================");
			FileUtils.writeLines(__output, lines, true);
			lines.clear();
		}
	}
}
