package logia.zara.process;

import java.awt.Cursor;
import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import logia.zara.controller.GetUrlController;
import logia.zara.db.Data;
import logia.zara.db.DataDAO;
import logia.zara.model.SaleProductData;
import logia.zara.view.TrackSaleFrame;

/**
 * The Class TrackSaleProcess.
 *
 * @author Paul Mai
 */
public class TrackSaleProcess extends Thread {

	/** The Constant LOGGER. */
	private static final Logger		LOGGER	= Logger.getLogger(TrackSaleProcess.class);

	/** The frame. */
	private final TrackSaleFrame	frame;

	/**
	 * Instantiates a new track sale process.
	 *
	 * @param __frame the frame
	 */
	public TrackSaleProcess(TrackSaleFrame __frame) {
		super();
		this.frame = __frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		this.frame.getBtnBrowse().setEnabled(true);
		this.frame.getBtnStart().setEnabled(true);
		this.frame.getBtnStop().setEnabled(false);

		super.interrupt();
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
		this.frame.getBtnBrowse().setEnabled(false);
		this.frame.getBtnStart().setEnabled(false);
		this.frame.getBtnStop().setEnabled(true);

		// Get list link from wish list file
		try {
			List<String> _listUrls = FileUtils
			        .readLines(new File(this.frame.getTxfWishList().getText()));
			// Run scan data from url
			GetUrlController _controller = new GetUrlController();
			for (String _url : _listUrls) {
				SaleProductData _newSaleProduct = _controller.scanUrl(_url);
				SaleProductData _savedSaleProduct = DataDAO.getInstance()
				        .get(_newSaleProduct.getRef()).toSaleProductData();
				// TODO check if price is lower, notify throw email
				if (_newSaleProduct.getProductData().getProductPrice() < _savedSaleProduct
				        .getProductData().getProductPrice()) {
					// Send notify
				}

				// Update newest product to DB
				DataDAO.getInstance().set(new Data(_newSaleProduct));

			}
		}
		catch (Exception _e) {
			TrackSaleProcess.LOGGER.error("Error when track sale product", _e);
		}
	}
}
