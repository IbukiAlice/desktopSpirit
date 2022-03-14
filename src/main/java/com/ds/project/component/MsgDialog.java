package com.ds.project.component;

import com.ds.project.Constants;
import com.ds.project.preset.FollowDialog;

import javax.swing.*;
import java.awt.*;

/**
 * 左侧对话框
 * @author IbukiAlice
 */
public class MsgDialog extends FollowDialog {

	private JTextArea textArea;

	public MsgDialog(Window window) {
		super(window);

		JPanel panel = new JPanel(null);
		panel.setOpaque(false);
		getContentPane().add(panel);

		textArea = new JTextArea();
		textArea.setBounds(20, 20 , 270, 70);
		textArea.setOpaque(false);
		textArea.setFont(Constants.DEFAULT_FONT);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setColumns(20);
		textArea.setRows(4);

		panel.add(textArea);
	}

	/**
	 * 获取背景图片
	 * @return	背景图片
	 */
	@Override
	public ImageIcon getBackgroundIcon(){
		return new ImageIcon(this.getClass().getResource("/pics/msg.png"));
	}

	/**
	 * 根据主窗口的位置信息，设置自身的位置
	 *
	 * @param force 是否强制调整位置
	 *              如为true，则无论窗体是否可见都更新位置
	 *              如为false，则仅窗体可见时更新位置
	 */
	@Override
	public void alterLocation(boolean force) {
		if(!force && !isVisible()) return;

		// 获取主窗口位置信息
		Rectangle r = getOwner().getBounds();

		// 保持在主窗口的左上角
		int newX = r.x + - getWidth();
		int newY = r.y - getHeight();

		setLocation(newX, newY);
	}

	public void updateText(String text){
		textArea.setText(text);
	}
}
