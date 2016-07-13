package logia.zara.application;

import java.awt.EventQueue;
import java.io.File;

import org.apache.log4j.Logger;

import logia.zara.view.GetUrlFrame;

/**
 * The Class Application.
 *
 * @author Paul Mai
 */
public final class Application {

	/** The Constant LOGGER. */
	private static final Logger	LOGGER	= Logger.getLogger(Application.class);

	/** The _frame. */
	private GetUrlFrame			_frame;

	public static final File	DB		= new File("/home/logia193/Desktop/ZaraDB.json");

	/**
	 * Create the application.
	 */
	public Application() {
		this.initialize();
	}

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					Application window = new Application();
					window._frame.setVisible(true);
				}
				catch (Exception e) {
					Application.LOGGER.error("Error when running application", e);
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this._frame = new GetUrlFrame();
	}

}
