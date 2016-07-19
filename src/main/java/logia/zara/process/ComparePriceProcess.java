package logia.zara.process;

import java.awt.Cursor;

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
	}

}
