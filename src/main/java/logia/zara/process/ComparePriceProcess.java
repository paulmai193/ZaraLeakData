package logia.zara.process;

import java.awt.Cursor;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import logia.zara.controller.GetUrlController;
import logia.zara.model.SaleProductData;
import logia.zara.view.ComparePriceFrame;

/**
 * The Class ComparePriceProcess.
 *
 * @author Paul Mai
 */
public class ComparePriceProcess extends Thread {

	/** The Constant LOGGER. */
	private static final Logger		LOGGER	= Logger.getLogger(ComparePriceProcess.class);

	/** The frame. */
	private final ComparePriceFrame	frame;

	/**
	 * Instantiates a new compare price process.
	 *
	 * @param __frame the frame
	 */
	public ComparePriceProcess(ComparePriceFrame __frame) {
		super();
		this.frame = __frame;
	}

	/**
	 * End.
	 */
	private void end() {
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.frame.getTxfLink().setEnabled(true);
		this.frame.getBtnRun().setEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		super.interrupt();
		this.end();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// Disable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.frame.getTxfLink().setEnabled(false);
		this.frame.getBtnRun().setEnabled(false);

		try {
			// Get chosen countries
			String[] _selectedCountries = { null, null, null, null, null };
			int _i = 0;
			for (JCheckBox _each : this.frame.getCheckBoxsCountries()) {
				if (_each.isSelected()) {
					_selectedCountries[_i] = _each.getText().toLowerCase();
					_i++;
				}
			}

			// Process the input link
			String _link = this.frame.getTxfLink().getText();
			URL _url = new URL(_link);
			// if (_link.isEmpty()) {
			// throw new IllegalArgumentException("Link cannot be empty");
			// }
			String _tmpPrefix = "http://www.zara.com/";
			String _tmpSurfix = _url.getPath().substring(3);
			GetUrlController _controller = new GetUrlController();
			SaleProductData _cheapestProduct = null;
			for (String _country : _selectedCountries) {
				if (_country != null) {
					String _tmpLink = _tmpPrefix + _country + _tmpSurfix;
					ComparePriceProcess.LOGGER.debug("Temp Link " + _tmpLink);

					// Scan url
					try {
						SaleProductData _tmpSaleProduct = _controller.scanUrl(_tmpLink);
						if (_cheapestProduct == null || _tmpSaleProduct.getProductData()
						        .getProductPrice() < _cheapestProduct.getProductData()
						                .getProductPrice()) {
							_cheapestProduct = _tmpSaleProduct;
						}
					}
					catch (InterruptedException __ex) {
						// Just warning this exception
						ComparePriceProcess.LOGGER.warn(__ex.getMessage());
					}

				}
			}
			String _msg;
			if (_cheapestProduct == null) {
				_msg = "Không tìm thấy sản phẩm tới link tương ứng.";
			}
			else {
				_msg = "Sản phẩm rẻ nhất:\n";
				_msg += "Tên: " + _cheapestProduct.getProductData().getProductName() + "\n";
				_msg += "Giá: " + _cheapestProduct.getProductData().getProductPrice() + " "
				        + _cheapestProduct.getProductData().getCurrency() + "\n";
				_msg += "Link: " + _cheapestProduct.getProductData().getLink() + "\n";
			}

			JOptionPane.showMessageDialog(this.frame, _msg, "Sản phẩm rẻ nhất",
			        JOptionPane.INFORMATION_MESSAGE);
		}
		catch (MalformedURLException __ex) {
			JOptionPane.showMessageDialog(this.frame, "Link không đúng", "",
			        JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception __ex) {
			ComparePriceProcess.LOGGER.error(__ex.getMessage(), __ex);
		}
		finally {
			this.end();
		}
	}
}
