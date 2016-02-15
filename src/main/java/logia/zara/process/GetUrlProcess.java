package logia.zara.process;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.IOException;

import javax.swing.JFileChooser;

import logia.zara.controller.GetUrlController;
import logia.zara.view.GetUrlFrame;

/**
 * The Class GetUrlProcess.
 *
 * @author Paul Mai
 */
public final class GetUrlProcess extends Thread {

	/** The frame. */
	private final GetUrlFrame frame;

	/**
	 * Instantiates a new gets the url process.
	 *
	 * @param frame the frame
	 */
	public GetUrlProcess(GetUrlFrame frame) {
		this.frame = frame;
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
		this.frame.get_btnRun().setEnabled(false);
		this.frame.get_btnBrowse().setEnabled(false);
		this.frame.get_txfLink().setEnabled(false);

		// Run scan data from url
		GetUrlController controller = new GetUrlController();
		controller.scanUrl(this.frame.get_txfLink().getText(), this.frame.get_txfOutput().getText(), this.frame.get_progressBar());

		// Enable UI components
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.frame.get_btnRun().setEnabled(true);
		this.frame.get_btnBrowse().setEnabled(true);
		this.frame.get_txfLink().setEnabled(true);

		JFileChooser chooseOutputDirectory = new JFileChooser(this.frame.get_txfOutput().getText());
		chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = chooseOutputDirectory.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				Desktop.getDesktop().open(chooseOutputDirectory.getSelectedFile());
			}
			catch (IOException e) {
				// Swallow this exception, not important
			}
		}
	}

}
