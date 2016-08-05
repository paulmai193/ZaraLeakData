package logia.zara.process;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.IOException;

import javax.swing.JFileChooser;

import logia.zara.controller.GetUrlController;
import logia.zara.view.ScanUrlFrame;

/**
 * The Class ScanUrlProcess.
 *
 * @author Paul Mai
 */
public final class ScanUrlProcess extends Thread {

	/** The Constant MAX. */
	public static final int		MAX			= 10;

	/** The Constant MIN. */
	public static final int		MIN			= 1;

	/** The number running process. */
	public static int			numProcess	= 0;

	/** The frame. */
	private final ScanUrlFrame	frame;

	/**
	 * Instantiates a new scan url process.
	 *
	 * @param __frame the frame
	 */
	public ScanUrlProcess(ScanUrlFrame __frame) {
		this.frame = __frame;
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
		this.frame.getBtnRun().setEnabled(false);
		this.frame.getBtnBrowse().setEnabled(false);
		this.frame.getTxfLink().setEnabled(false);

		// Run scan data from url
		GetUrlController _controller = new GetUrlController();
		_controller.scanUrl(this.frame.getTxfLink().getText(), this.frame.getTxfOutput().getText(),
				this.frame.getProgressBar());

		// Enable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.frame.getBtnRun().setEnabled(true);
		this.frame.getBtnBrowse().setEnabled(true);
		this.frame.getTxfLink().setEnabled(true);

		JFileChooser _chooseOutputDirectory = new JFileChooser(this.frame.getTxfOutput().getText());
		_chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int _returnVal = _chooseOutputDirectory.showOpenDialog(null);
		if (_returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				Desktop.getDesktop().open(_chooseOutputDirectory.getSelectedFile());
			}
			catch (IOException e) {
				// Swallow this exception, not important
			}
		}
	}

}
