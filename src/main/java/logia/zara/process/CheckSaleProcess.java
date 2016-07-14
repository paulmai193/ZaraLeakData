package logia.zara.process;

import java.awt.Cursor;
import java.io.File;
import java.util.List;

import logia.zara.controller.GetUrlController;
import logia.zara.view.CheckSaleFrame;

import org.apache.commons.io.FileUtils;

public class CheckSaleProcess extends Thread {

	private final CheckSaleFrame frame;

	public CheckSaleProcess(CheckSaleFrame __frame) {
		super();
		this.frame = __frame;
	}

	@Override
	public void run() {
		// Disable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.frame.getBtnBrowse().setEnabled(false);
		this.frame.getBtnStart().setEnabled(false);
		this.frame.getBtnStop().setEnabled(true);

		// Get list link from wish list file
		try {
			List<String> _listUrls = FileUtils.readLines(new File(this.frame.getTxfWishList().getText()));
			// Run scan data from url
			GetUrlController _controller = new GetUrlController();
			for (String _url : _listUrls) {
				_controller.scanUrl(_url);
			}
		}
		catch (Exception _e) {
			_e.printStackTrace();
		}
	}

	@Override
	public void interrupt() {
		this.frame.getBtnBrowse().setEnabled(true);
		this.frame.getBtnStart().setEnabled(true);
		this.frame.getBtnStop().setEnabled(false);

		super.interrupt();
	}
}
