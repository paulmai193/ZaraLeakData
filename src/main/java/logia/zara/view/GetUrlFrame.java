package logia.zara.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
import logia.zara.process.GetUrlProcess;

/**
 * The Class GetUrlFrame.
 *
 * @author Paul Mai
 */
public class GetUrlFrame extends JFrame {

	// /** The Constant LOGGER. */
	// private static final Logger LOGGER = Logger.getLogger(GetUrlFrame.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The _btn browse. */
	private JButton           _btnBrowse;

	/** The _btn new button. */
	private JButton           _btnRun;

	/** The _content pane. */
	private JPanel            _contentPane;

	/** The _lbl link. */
	private JLabel            _lblLink;

	/** The _lbl output. */
	private JLabel            _lblOutput;

	/** The _progress bar. */
	private JProgressBar      _progressBar;

	/** The _sp button. */
	private JSplitPane        _spButton;

	/** The _sp component. */
	private JSplitPane        _spComponent;

	/** The _sp label. */
	private JSplitPane        _spLabel;

	/** The _text field. */
	private JTextField        _txfLink;

	/** The _txf output. */
	private JTextField        _txfOutput;

	/**
	 * Create the frame.
	 */
	public GetUrlFrame() {
		this.setResizable(false);
		this.setTitle("Export Data");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(450, 150);
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this._contentPane = new JPanel();
		this._contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this._contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this._contentPane);

		JLabel lblExportZaraData = new JLabel("EXPORT ZARA DATA FROM LINK");
		lblExportZaraData.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblExportZaraData.setHorizontalAlignment(SwingConstants.CENTER);
		this._contentPane.add(lblExportZaraData, BorderLayout.NORTH);

		this._progressBar = new JProgressBar();
		this._progressBar.setMinimum(GetUrlController.MIN);
		this._progressBar.setMaximum(GetUrlController.MAX);
		this._progressBar.setValue(0);
		this._progressBar.setStringPainted(true);
		this._progressBar.setString("");

		this._contentPane.add(this._progressBar, BorderLayout.SOUTH);

		this._spLabel = new JSplitPane();
		this._spLabel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this._contentPane.add(this._spLabel, BorderLayout.WEST);

		this._lblLink = new JLabel("Link");
		this._lblLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._lblLink.setHorizontalAlignment(SwingConstants.CENTER);
		this._spLabel.setRightComponent(this._lblLink);

		this._lblOutput = new JLabel("Output");
		this._lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		this._spLabel.setLeftComponent(this._lblOutput);

		this._spComponent = new JSplitPane();
		this._spComponent.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this._contentPane.add(this._spComponent, BorderLayout.CENTER);

		this._txfLink = new JTextField();
		this._spComponent.setRightComponent(this._txfLink);
		this._txfLink.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					GetUrlProcess process = new GetUrlProcess(GetUrlFrame.this);
					process.start();
				}
			}
		});
		this._txfLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._txfLink.setToolTipText("Type or copy Zara's product link here");
		this._txfLink.setColumns(10);

		this._txfOutput = new JTextField(System.getProperty("user.dir") + File.separator + "output");
		this._txfOutput.setEditable(false);
		this._txfOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._spComponent.setLeftComponent(this._txfOutput);
		this._txfOutput.setColumns(10);

		this._spButton = new JSplitPane();
		this._spButton.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this._contentPane.add(this._spButton, BorderLayout.EAST);

		this._btnRun = new JButton("Run");
		this._btnRun.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._spButton.setRightComponent(this._btnRun);

		this._btnBrowse = new JButton("Browse");
		this._btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this._btnBrowse.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooseOutputDirectory = new JFileChooser(GetUrlFrame.this._txfOutput.getText());
				chooseOutputDirectory.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = chooseOutputDirectory.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooseOutputDirectory.getSelectedFile();
					GetUrlFrame.this._txfOutput.setText(file.getAbsolutePath());
				}
			}
		});
		this._spButton.setLeftComponent(this._btnBrowse);
		this._btnRun.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				GetUrlProcess process = new GetUrlProcess(GetUrlFrame.this);
				process.start();
			}
		});
	}

	/**
	 * Gets the _btn browse.
	 *
	 * @return the _btnBrowse
	 */
	public JButton get_btnBrowse() {
		return this._btnBrowse;
	}

	/**
	 * Gets the _btn run.
	 *
	 * @return the _btn run
	 */
	public JButton get_btnRun() {
		return this._btnRun;
	}

	/**
	 * Gets the _content pane.
	 *
	 * @return the _contentPane
	 */
	public JPanel get_contentPane() {
		return this._contentPane;
	}

	/**
	 * Gets the _lbl link.
	 *
	 * @return the _lblLink
	 */
	public JLabel get_lblLink() {
		return this._lblLink;
	}

	/**
	 * Gets the _lbl output.
	 *
	 * @return the _lblOutput
	 */
	public JLabel get_lblOutput() {
		return this._lblOutput;
	}

	/**
	 * Gets the _progress bar.
	 *
	 * @return the _progressBar
	 */
	public JProgressBar get_progressBar() {
		return this._progressBar;
	}

	/**
	 * Gets the _sp button.
	 *
	 * @return the _spButton
	 */
	public JSplitPane get_spButton() {
		return this._spButton;
	}

	/**
	 * Gets the _sp component.
	 *
	 * @return the _spComponent
	 */
	public JSplitPane get_spComponent() {
		return this._spComponent;
	}

	/**
	 * Gets the _sp label.
	 *
	 * @return the _spLabel
	 */
	public JSplitPane get_spLabel() {
		return this._spLabel;
	}

	/**
	 * Gets the _txf link.
	 *
	 * @return the _txfLink
	 */
	public JTextField get_txfLink() {
		return this._txfLink;
	}

	/**
	 * Gets the _txf output.
	 *
	 * @return the _txfOutput
	 */
	public JTextField get_txfOutput() {
		return this._txfOutput;
	}
}
