package com.ds.project.component;

import com.ds.project.Constants;
import com.ds.project.data.ClockData;
import com.ds.project.data.DataSource;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 每条时间记录面板
 * @author IbukiAlice
 */
public class ClockItemPanel extends JPanel {

	private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm");

	/** 各种按钮图标 */
	private static final ImageIcon[] icons = {
		new ImageIcon(ClockItemPanel.class.getResource("/pics/button/confirm.png")),
		new ImageIcon(ClockItemPanel.class.getResource("/pics/button/remove.png"))
	};

	private JLabel idLabel;
	private JTextField time;
	private JTextField text;

	private int clockId;

	public ClockItemPanel(int id, TopEditableDialog owner){
		this.clockId = id;

		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));		// 设置布局
		setOpaque(false);													// 面板透明

		// 添加其它元素
		idLabel = new JLabel();
		time = new JTextField(5);
		text = new JTextField(20);

		idLabel.setSize(30, 25);
		idLabel.setFont(Constants.DEFAULT_FONT);
		time.setFont(Constants.DEFAULT_FONT);
		text.setFont(Constants.DEFAULT_FONT);

		add(idLabel);
		add(time);
		add(text);

		updateInfo(id);

		JButton confirm = new JButton(icons[0]);
		confirm.setPreferredSize(new Dimension(icons[0].getIconWidth() + 2, icons[0].getIconHeight()));
		confirm.setBorderPainted(false);
		confirm.setContentAreaFilled(false);
		add(confirm);

		JButton remove = new JButton(icons[1]);
		remove.setPreferredSize(new Dimension(icons[1].getIconWidth() + 2, icons[1].getIconHeight()));
		remove.setBorderPainted(false);
		remove.setContentAreaFilled(false);
		add(remove);

		confirm.addActionListener(e -> {
			try {
				long timeMilli = df.parse(time.getText()).getTime();
				DataSource.me().resetClock(clockId, timeMilli, text.getText());
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		});

		remove.addActionListener(e -> owner.removeSelfAndUpdateOtherClockItemPanel(this));
	}

	public int getClockId(){
		return clockId;
	}

	/**
	 * 更新闹钟信息
	 * @param clockId	闹钟数据ID
	 */
	public void updateInfo(int clockId){
		ClockData clockData = DataSource.me().getClock(clockId);

		idLabel.setText(String.format("%02d", clockId + 1));
		if(clockData.getShowDate() != null) time.setText(clockData.getShowDate());
		if(clockData.getText() != null) text.setText(clockData.getText());
	}
}
