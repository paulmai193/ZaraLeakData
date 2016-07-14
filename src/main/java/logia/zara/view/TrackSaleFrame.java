package logia.zara.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logia.zara.process.TrackSaleProcess;

/**
 * The Class TrackSaleFrame.
 *
 * @author Paul Mai
 */
public class TrackSaleFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/** The btn browse. */
	private JButton				btnBrowse;

	/** The btn start. */
	private JButton				btnStart;

	/** The btn stop. */
	private JButton				btnStop;

	/** The content pane. */
	private JPanel				contentPane;

	/** The process. */
	private TrackSaleProcess	process;

	/** The txf wish list. */
	private JTextField			txfWishList;

	/**
	 * Instantiates a new track sale frame.
	 */
	public TrackSaleFrame() {
		this.setTitle("Kiểm tra sản phẩm sale");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 131);

		Dimension _dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(_dim.width / 2 - this.getSize().width / 2,
				_dim.height / 2 - this.getSize().height / 2);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);

		JLabel _lblWishList = new JLabel("Danh sách link sản phẩm");
		_lblWishList.setHorizontalAlignment(SwingConstants.CENTER);
		_lblWishList.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.contentPane.add(_lblWishList, BorderLayout.NORTH);

		this.txfWishList = new JTextField();
		this.txfWishList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.txfWishList.setEditable(false);
		this.txfWishList.setColumns(10);
		this.txfWishList.setPreferredSize(new Dimension(300, 25));
		this.contentPane.add(this.txfWishList, BorderLayout.CENTER);

		this.btnBrowse = new JButton("Browse");
		this.btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				JFileChooser chooseOutputDirectory = new JFileChooser(
						TrackSaleFrame.this.txfWishList.getText());
				chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int _returnVal = chooseOutputDirectory.showOpenDialog(null);
				if (_returnVal == JFileChooser.APPROVE_OPTION) {
					File _file = chooseOutputDirectory.getSelectedFile();
					TrackSaleFrame.this.txfWishList.setText(_file.getAbsolutePath());
				}
			}
		});
		this.btnBrowse.setPreferredSize(new Dimension(100, 25));
		this.contentPane.add(this.btnBrowse, BorderLayout.EAST);

		JPanel _panelExecute = new JPanel();
		this.contentPane.add(_panelExecute, BorderLayout.SOUTH);

		this.btnStart = new JButton("Bắt đầu");
		this.btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				TrackSaleFrame.this.process = new TrackSaleProcess(TrackSaleFrame.this);
				TrackSaleFrame.this.process.start();
			}
		});
		this.btnStart.setPreferredSize(new Dimension(100, 25));
		_panelExecute.add(this.btnStart);

		this.btnStop = new JButton("Dừng");
		this.btnStop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				TrackSaleFrame.this.process.interrupt();
				TrackSaleFrame.this.process = null;
			}
		});
		this.btnStop.setEnabled(false);
		this.btnStop.setPreferredSize(new Dimension(100, 25));
		_panelExecute.add(this.btnStop);
	}

	/**
	 * Instantiates a new track sale frame.
	 *
	 * @param __menuFrame the __menu frame
	 */
	public TrackSaleFrame(final MenuFrame __menuFrame) {
		this();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent __e) {
				__menuFrame.setVisible(true);
			}
		});
	}

	/**
	 * Gets the btn browse.
	 *
	 * @return the btnBrowse
	 */
	public JButton getBtnBrowse() {
		return this.btnBrowse;
	}

	/**
	 * Gets the btn start.
	 *
	 * @return the btnStart
	 */
	public JButton getBtnStart() {
		return this.btnStart;
	}

	/**
	 * Gets the btn stop.
	 *
	 * @return the btnStop
	 */
	public JButton getBtnStop() {
		return this.btnStop;
	}

	/**
	 * Gets the content pane.
	 *
	 * @return the contentPane
	 */
	@Override
	public JPanel getContentPane() {
		return this.contentPane;
	}

	/**
	 * Gets the txf wish list.
	 *
	 * @return the txfWishList
	 */
	public JTextField getTxfWishList() {
		return this.txfWishList;
	}

}
