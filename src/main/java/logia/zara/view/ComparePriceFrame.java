package logia.zara.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logia.zara.process.ComparePriceProcess;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ComparePriceFrame extends JFrame {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JPanel            contentPane;
	private JTextField        txfLink;
	private JButton           btnRun;

	/**
	 * Create the frame.
	 */
	public ComparePriceFrame() {
		setTitle("So sánh giá");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 191);

		Dimension _dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(_dim.width / 2 - this.getSize().width / 2, _dim.height / 2 - this.getSize().height / 2);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JLabel _lblComparePrice = new JLabel("So sánh và tìm giá sản phẩm giữa các quốc gia");
		_lblComparePrice.setHorizontalAlignment(SwingConstants.CENTER);
		_lblComparePrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(_lblComparePrice, BorderLayout.NORTH);

		JPanel _panelContent = new JPanel();
		contentPane.add(_panelContent, BorderLayout.CENTER);
		_panelContent.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("left:pref"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
		        ColumnSpec.decode("pref:grow"), FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("right:pref"), }, new RowSpec[] {
		        FormSpecs.PREF_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormSpecs.LINE_GAP_ROWSPEC,
		        FormSpecs.PREF_ROWSPEC, }));

		JLabel _lblLink = new JLabel("Link");
		_lblLink.setHorizontalAlignment(SwingConstants.RIGHT);
		_lblLink.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_lblLink.setPreferredSize(new Dimension(50, 25));
		_panelContent.add(_lblLink, "1, 1, right, default");

		txfLink = new JTextField();
		_panelContent.add(txfLink, "3, 1, fill, default");
		txfLink.setColumns(10);

		JLabel _lblCountry = new JLabel("Country");
		_lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_lblCountry.setHorizontalAlignment(SwingConstants.RIGHT);
		_panelContent.add(_lblCountry, "1, 3, right, top");

		JPanel _panel = new JPanel();
		_panelContent.add(_panel, "3, 3, fill, fill");
		_panel.setLayout(new GridLayout(1, 0, 0, 0));

		JCheckBox _chckbxCn = new JCheckBox("CN");
		_chckbxCn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_panel.add(_chckbxCn);

		JCheckBox _chckbxDe = new JCheckBox("DE");
		_chckbxDe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_panel.add(_chckbxDe);

		JCheckBox _chckbxEs = new JCheckBox("ES");
		_chckbxEs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_panel.add(_chckbxEs);

		JCheckBox _chckbxUk = new JCheckBox("UK");
		_chckbxUk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_panel.add(_chckbxUk);

		JCheckBox _chckbxUs = new JCheckBox("US");
		_chckbxUs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		_panel.add(_chckbxUs);

		btnRun = new JButton("Thực hiện");
		btnRun.setPreferredSize(new Dimension(50, 25));
		btnRun.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				ComparePriceProcess _process = new ComparePriceProcess(ComparePriceFrame.this);
				_process.start();
			}
		});
		_panelContent.add(btnRun, "3, 5");
	}

	public ComparePriceFrame(final MenuFrame __menuFrame) {
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

	public JTextField getTxfLink() {
		return this.txfLink;
	}

	/**
	 * @return the btnRun
	 */
	public JButton getBtnRun() {
		return this.btnRun;
	}

}