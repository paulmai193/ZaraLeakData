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
import javax.swing.border.EmptyBorder;

import logia.zara.process.CheckSaleProcess;

/**
 * The Class CheckSaleFrame.
 *
 * @author Paul Mai
 */
public class CheckSaleFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The content pane. */
	private JPanel            contentPane;
	private JTextField        txfWishList;
	private JButton           btnBrowse;
	private JButton           btnStart;
	private JButton           btnStop;
	private CheckSaleProcess  process;

	/**
	 * Instantiates a new check sale frame.
	 */
	public CheckSaleFrame() {
		setTitle("Kiểm tra sản phẩm sale");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 131);

		Dimension _dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(_dim.width / 2 - this.getSize().width / 2, _dim.height / 2 - this.getSize().height / 2);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel _lblWishList = new JLabel("Danh sách link sản phẩm");
		_lblWishList.setHorizontalAlignment(SwingConstants.CENTER);
		_lblWishList.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(_lblWishList, BorderLayout.NORTH);

		txfWishList = new JTextField();
		txfWishList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txfWishList.setEditable(false);
		txfWishList.setColumns(10);
		txfWishList.setPreferredSize(new Dimension(300, 25));
		contentPane.add(txfWishList, BorderLayout.CENTER);

		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				JFileChooser chooseOutputDirectory = new JFileChooser(CheckSaleFrame.this.txfWishList.getText());
				chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int _returnVal = chooseOutputDirectory.showOpenDialog(null);
				if (_returnVal == JFileChooser.APPROVE_OPTION) {
					File _file = chooseOutputDirectory.getSelectedFile();
					CheckSaleFrame.this.txfWishList.setText(_file.getAbsolutePath());
				}
			}
		});
		btnBrowse.setPreferredSize(new Dimension(100, 25));
		contentPane.add(btnBrowse, BorderLayout.EAST);

		JPanel _panelExecute = new JPanel();
		contentPane.add(_panelExecute, BorderLayout.SOUTH);

		btnStart = new JButton("Bắt đầu");
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				process = new CheckSaleProcess(CheckSaleFrame.this);
				process.start();
			}
		});
		btnStart.setPreferredSize(new Dimension(100, 25));
		_panelExecute.add(btnStart);

		btnStop = new JButton("Dừng");
		btnStop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				process.interrupt();
				process = null;
			}
		});
		btnStop.setEnabled(false);
		btnStop.setPreferredSize(new Dimension(100, 25));
		_panelExecute.add(btnStop);
	}

	/**
	 * Instantiates a new check sale frame.
	 *
	 * @param __menuFrame the __menu frame
	 */
	public CheckSaleFrame(final MenuFrame __menuFrame) {
		this();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent __e) {
				__menuFrame.setVisible(true);
			}
		});
	}

	/**
	 * @return the contentPane
	 */
	public JPanel getContentPane() {
		return this.contentPane;
	}

	/**
	 * @return the txfWishList
	 */
	public JTextField getTxfWishList() {
		return this.txfWishList;
	}

	/**
	 * @return the btnBrowse
	 */
	public JButton getBtnBrowse() {
		return this.btnBrowse;
	}

	/**
	 * @return the btnStart
	 */
	public JButton getBtnStart() {
		return this.btnStart;
	}

	/**
	 * @return the btnStop
	 */
	public JButton getBtnStop() {
		return this.btnStop;
	}

}
