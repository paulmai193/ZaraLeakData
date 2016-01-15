package logia.zara.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public final class ExportToFile {

	public static void exportOnSalesProduct(File output, SaleProductData data) throws IOException {
		if (data.isOnSale()) {
			List<String> lines = new ArrayList<String>();

			lines.add(data.getProductName());
			FileUtils.writeLines(output, lines, true);
			lines.clear();

			lines.add(data.getProductPrice());
			FileUtils.writeLines(output, lines, true);
			lines.clear();

			FileUtils.writeLines(output, data.getProductSizes(), true);

			lines.add(data.getLink());
			FileUtils.writeLines(output, lines, true);
			lines.clear();

			lines.add("================================================");
			FileUtils.writeLines(output, lines, true);
			lines.clear();
		}

	}
}
