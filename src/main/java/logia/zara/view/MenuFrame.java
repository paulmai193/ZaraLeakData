package logia.zara.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame            frame;

	/**
	 */
	public MenuFrame() {
		this.setTitle("Menu");
		this.setBounds(100, 100, 220, 134);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension _dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(_dim.width / 2 - this.getSize().width / 2, _dim.height / 2 - this.getSize().height / 2);

		JPanel _panelButton = new JPanel();
		this.getContentPane().add(_panelButton, BorderLayout.CENTER);

		JButton _btnScanUrl = new JButton("Quét link");
		_btnScanUrl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent __e) {
				MenuFrame.this.frame = new ScanUrlFrame(MenuFrame.this);
				MenuFrame.this.frame.setVisible(true);
				MenuFrame.this.setVisible(false);
			}
		});
		_btnScanUrl.setPreferredSize(new Dimension(170, 25));
		_panelButton.add(_btnScanUrl);

		JButton _btnCheckSale = new JButton("Kiểm tra sản phẩm sale");
		_btnCheckSale.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent __e) {
				MenuFrame.this.frame = new CheckSaleFrame(MenuFrame.this);
				MenuFrame.this.frame.setVisible(true);
				MenuFrame.this.setVisible(false);
			}
		});
		_btnCheckSale.setPreferredSize(new Dimension(170, 25));
		_panelButton.add(_btnCheckSale);

		JButton _btnComparePrice = new JButton("So sánh giá");
		_btnComparePrice.setEnabled(false);
		_btnComparePrice.setPreferredSize(new Dimension(170, 25));
		_panelButton.add(_btnComparePrice);
	}

	public void callBack() {
		this.setVisible(true);
	}
}
