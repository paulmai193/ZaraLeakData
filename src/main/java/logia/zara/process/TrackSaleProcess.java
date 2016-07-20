package logia.zara.process;

import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Message.RecipientType;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import logia.utility.email.EmailUtil;
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
	private static final Logger				LOGGER	= Logger.getLogger(TrackSaleProcess.class);

	/** The frame. */
	private final TrackSaleFrame			frame;

	private final ScheduledExecutorService	SERVICE	= Executors.newSingleThreadScheduledExecutor();

	/**
	 * Instantiates a new track sale process.
	 *
	 * @param __frame the frame
	 */
	public TrackSaleProcess(TrackSaleFrame __frame) {
		super();
		this.frame = __frame;
	}

	/**
	 * End track.
	 */
	private void endTrack() {
		this.frame.getBtnBrowse().setEnabled(true);
		this.frame.getBtnStart().setEnabled(true);
		this.frame.getBtnStop().setEnabled(false);
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.SERVICE.shutdownNow();

		TrackSaleProcess.LOGGER.debug("End tracking products");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		super.interrupt();
		this.endTrack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		TrackSaleProcess.LOGGER.debug("Start tracking products");
		// Disable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.frame.getBtnBrowse().setEnabled(false);
		this.frame.getBtnStart().setEnabled(false);
		this.frame.getBtnStop().setEnabled(true);

		// Get list link from wish list file
		try {
			final List<String> _listUrls = FileUtils
			        .readLines(new File(this.frame.getTxfWishList().getText()));
			final GetUrlController _controller = new GetUrlController();

			// Run scan data from url
			Runnable _task = new Runnable() {

				@Override
				public void run() {
					TrackSaleProcess.LOGGER.debug("Track sale product");
					try {
						for (String _url : _listUrls) {
							SaleProductData _newSaleProduct = _controller.scanUrl(_url);
							SaleProductData _savedSaleProduct = null;
							Data _tmpData = DataDAO.getInstance().get(_newSaleProduct.getRef());
							if (_tmpData.getValue() != null && !_tmpData.getValue().isEmpty()) {
								_savedSaleProduct = _tmpData.toSaleProductData();
							}
							// check if price is lower, notify throw email
							if (_savedSaleProduct != null && (_newSaleProduct.getProductData()
			                        .getProductPrice() < _savedSaleProduct.getProductData()
			                                .getProductPrice()
			                        || true)) {
								String _title = "Sản phẩm " + _newSaleProduct.getRef()
			                            + " đang giảm giá";
								String _content = "Giá cũ: "
			                            + _savedSaleProduct.getProductData().getProductPrice() + " "
			                            + _savedSaleProduct.getProductData().getCurrency() + "<br>";
								_content += "Giá mới: "
			                            + _newSaleProduct.getProductData().getProductPrice() + " "
			                            + _newSaleProduct.getProductData().getCurrency() + "<br>";
								_content += "Link: " + _newSaleProduct.getProductData().getLink();

								// Send notify
								EmailUtil _emailUtil = new EmailUtil();
								_emailUtil.sendEmail("vngiay@yahoo.com", _title, _content,
			                            RecipientType.TO);
							}

							// Update newest product to DB
							DataDAO.getInstance().set(new Data(_newSaleProduct));
						}
					}
					catch (InterruptedException _ex) {
						TrackSaleProcess.LOGGER.error("Track sale product was terminated", _ex);
					}
					catch (Exception _ex) {
						TrackSaleProcess.LOGGER.error("Error when track sale product", _ex);
					}

				}
			};

			this.SERVICE.scheduleAtFixedRate(_task, 0, 6, TimeUnit.HOURS);

		}
		catch (FileNotFoundException _ex) {
			JOptionPane.showMessageDialog(this.frame,
			        "Vui lòng chọn Wishlist các sản phẩm cần theo dõi", "Không tìm thấy Wishlist",
			        JOptionPane.ERROR_MESSAGE);
			this.endTrack();
		}
		catch (Exception _ex) {
			TrackSaleProcess.LOGGER.error("Error when track sale product", _ex);
			JOptionPane.showMessageDialog(this.frame, "Lỗi: " + _ex.getMessage(), "Lỗi ứng dụng",
			        JOptionPane.ERROR_MESSAGE);
			this.endTrack();
		}
	}
}
