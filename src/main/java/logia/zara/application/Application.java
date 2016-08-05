package logia.zara.application;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import logia.redis.util.JedisFactory;
import logia.utility.email.EmailUtil;
import logia.zara.view.MenuFrame;

/**
 * The Class Application.
 *
 * @author Paul Mai
 */
public final class Application {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(Application.class);

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
					/* Init main application windows */
					Application window = new Application();
					window.frame.setVisible(true);

					/* Init Jedis pool */
					JedisFactory.getInstance().connect("localhost", 6379, 30, 0, 60000);

					/* Init email utility */
					EmailUtil.setPropertiesPath(EmailUtil.class.getClassLoader()
							.getResource("email.properties").toURI());

					/* Init program exit event */
					Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

						@Override
						public void run() {
							JedisFactory jedisFactory = JedisFactory.getInstance();
							try {
								jedisFactory.release();
							}
							catch (Exception _e) {
								// Swallow this exception
							}
						}
					}));
				}
				catch (Exception e) {
					Application.LOGGER.error("Error when running application", e);
				}
			}
		});
	}

	/** The frame. */
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Application() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new MenuFrame();
	}

}
