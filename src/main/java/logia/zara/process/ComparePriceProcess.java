package logia.zara.process;

import java.awt.Cursor;
import java.net.URL;

import javax.swing.JCheckBox;

import logia.zara.controller.GetUrlController;
import logia.zara.model.SaleProductData;
import logia.zara.view.ComparePriceFrame;

public class ComparePriceProcess extends Thread {

	private final ComparePriceFrame frame;

	public ComparePriceProcess(ComparePriceFrame __frame) {
		super();
		this.frame = __frame;
	}

	@Override
	public void run() {
		// Disable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.frame.getTxfLink().setEnabled(false);
		this.frame.getBtnRun().setEnabled(false);

		try {
			// Get chosen countries
			String[] _selectedCountries = {};
			int _i = 0;
			for (JCheckBox _each : this.frame.getCheckBoxsCountries()) {
				if (_each.isSelected()) {
					_selectedCountries[_i] = _each.getText();
					_i++;
				}
			}

			// Process the input link
			String _link = this.frame.getTxfLink().getText();
			URL _url = new URL(_link);
			if (_link.isEmpty()) {
				throw new IllegalArgumentException("Link cannot be empty");
			}
			String _tmpPrefix = "http://www.zara.com/";
			String _tmpSurfix = _url.getPath().substring(3);
			GetUrlController _controller = new GetUrlController();
			for (String _country : _selectedCountries) {
				String _tmpLink = _tmpPrefix + _country + _tmpSurfix;

				// Scan url
				SaleProductData _saleProduct = _controller.scanUrl(_tmpLink);
			}
		}
		catch (Exception ___ex) {
			// TODO: handle exception
		}

	}

}
