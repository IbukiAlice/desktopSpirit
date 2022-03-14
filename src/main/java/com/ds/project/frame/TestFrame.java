package com.ds.project.frame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestFrame extends JDialog {

	public TestFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(100, 100);

		JPanel panel = new JPanel();				// 无布局
		getContentPane().add(panel);


		JTextField text = new JTextField(4);
		panel.add(text);

		setVisible(true);
	}

	@Override
	public void dispose() {
		super.dispose();

//		System.exit(0);
	}

	//	@Override
	public ImageIcon getBackgroundIcon() {
		return new ImageIcon(this.getClass().getResource("/pics/msg.png"));
	}

	/**
	 * 根据主窗口的位置信息，设置自身的位置
	 *
	 * @param force 是否强制调整位置
	 *              如为true，则无论窗体是否可见都更新位置
	 *              如为false，则仅窗体可见时更新位置
	 */
//	@Override
	public void alterLocation(boolean force) {

	}
}
