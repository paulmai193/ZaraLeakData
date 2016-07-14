package logia.zara.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logia.zara.controller.GetUrlController;
import logia.zara.process.ScanUrlProcess;

/**
 * The Class ScanUrlFrame.
 *
 * @author Paul Mai
 */
public class ScanUrlFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The button browse. */
	private JButton           btnBrowse;

	/** The button new button. */
	private JButton           btnRun;

	/** The content pane. */
	private JPanel            contentPane;

	/** The lable link. */
	private JLabel            lblLink;

	/** The lable output. */
	private JLabel            lblOutput;

	/** The progress bar. */
	private JProgressBar      progressBar;

	/** The split button. */
	private JSplitPane        spButton;

	/** The split component. */
	private JSplitPane        spComponent;

	/** The split label. */
	private JSplitPane        spLabel;

	/** The text field link. */
	private JTextField        txfLink;

	/** The text field output. */
	private JTextField        txfOutput;

	/**
	 */
	public ScanUrlFrame() {
		this.setResizable(false);
		this.setTitle("Quét link");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Dimension _dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(450, 150);
		this.setLocation(_dim.width / 2 - this.getSize().width / 2, _dim.height / 2 - this.getSize().height / 2);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);

		JLabel _lblExportZaraData = new JLabel("EXPORT ZARA DATA FROM LINK");
		_lblExportZaraData.setFont(new Font("Tahoma", Font.PLAIN, 15));
		_lblExportZaraData.setHorizontalAlignment(SwingConstants.CENTER);
		this.contentPane.add(_lblExportZaraData, BorderLayout.NORTH);

		this.progressBar = new JProgressBar();
		this.progressBar.setMinimum(GetUrlController.MIN);
		this.progressBar.setMaximum(GetUrlController.MAX);
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		this.progressBar.setString("");

		this.contentPane.add(this.progressBar, BorderLayout.SOUTH);

		this.spLabel = new JSplitPane();
		this.spLabel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.contentPane.add(this.spLabel, BorderLayout.WEST);

		this.lblLink = new JLabel("Link");
		this.lblLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.lblLink.setHorizontalAlignment(SwingConstants.CENTER);
		this.spLabel.setRightComponent(this.lblLink);

		this.lblOutput = new JLabel("Output");
		this.lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		this.spLabel.setLeftComponent(this.lblOutput);

		this.spComponent = new JSplitPane();
		this.spComponent.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.contentPane.add(this.spComponent, BorderLayout.CENTER);

		this.txfLink = new JTextField();
		this.spComponent.setRightComponent(this.txfLink);
		this.txfLink.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent __e) {
				if (__e.getKeyCode() == KeyEvent.VK_ENTER) {
					ScanUrlProcess _process = new ScanUrlProcess(ScanUrlFrame.this);
					_process.start();
				}
			}
		});
		this.txfLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.txfLink.setToolTipText("Type or copy Zara's product link here");
		this.txfLink.setColumns(10);

		this.txfOutput = new JTextField(System.getProperty("user.dir") + File.separator + "output" + File.separator + "products ("
		        + new SimpleDateFormat("dd MMM, yyyy").format(new Date()) + ").pdf");
		this.txfOutput.setEditable(false);
		this.txfOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.spComponent.setLeftComponent(this.txfOutput);
		this.txfOutput.setColumns(10);

		this.spButton = new JSplitPane();
		this.spButton.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.contentPane.add(this.spButton, BorderLayout.EAST);

		this.btnRun = new JButton("Run");
		this.btnRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent __e) {
				ScanUrlProcess _process = new ScanUrlProcess(ScanUrlFrame.this);
				_process.start();
			}
		});
		this.btnRun.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.spButton.setRightComponent(this.btnRun);

		this.btnBrowse = new JButton("Browse");
		this.btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.btnBrowse.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent __e) {
				JFileChooser _chooseOutputDirectory = new JFileChooser(ScanUrlFrame.this.txfOutput.getText());
				_chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int _returnVal = _chooseOutputDirectory.showOpenDialog(null);
				if (_returnVal == JFileChooser.APPROVE_OPTION) {
					File _file = _chooseOutputDirectory.getSelectedFile();
					ScanUrlFrame.this.txfOutput.setText(_file.getAbsolutePath());
				}
			}
		});
		this.spButton.setLeftComponent(this.btnBrowse);
	}

	/**
	 * Instantiates a new scan url frame.
	 *
	 * @param __menuFrame the __menu frame
	 */
	public ScanUrlFrame(final MenuFrame __menuFrame) {
		this();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent __e) {
				__menuFrame.setVisible(true);
			}
		});
	}

	/**
	 * Gets the button browse.
	 *
	 * @return the btnBrowse
	 */
	public JButton getBtnBrowse() {
		return this.btnBrowse;
	}

	/**
	 * Gets the button run.
	 *
	 * @return the btnRun
	 */
	public JButton getBtnRun() {
		return this.btnRun;
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
	 * Gets the lable link.
	 *
	 * @return the lblLink
	 */
	public JLabel getLblLink() {
		return this.lblLink;
	}

	/**
	 * Gets the lable output.
	 *
	 * @return the lblOutput
	 */
	public JLabel getLblOutput() {
		return this.lblOutput;
	}

	/**
	 * Gets the progress bar.
	 *
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return this.progressBar;
	}

	/**
	 * Gets the split button.
	 *
	 * @return the spButton
	 */
	public JSplitPane getSpButton() {
		return this.spButton;
	}

	/**
	 * Gets the split component.
	 *
	 * @return the spComponent
	 */
	public JSplitPane getSpComponent() {
		return this.spComponent;
	}

	/**
	 * Gets the split label.
	 *
	 * @return the spLabel
	 */
	public JSplitPane getSpLabel() {
		return this.spLabel;
	}

	/**
	 * Gets the text field link.
	 *
	 * @return the txfLink
	 */
	public JTextField getTxfLink() {
		return this.txfLink;
	}

	/**
	 * Gets the text field output.
	 *
	 * @return the txfOutput
	 */
	public JTextField getTxfOutput() {
		return this.txfOutput;
	}
}
