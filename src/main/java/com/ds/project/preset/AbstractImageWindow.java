package com.ds.project.preset;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractImageWindow extends JDialog {

	public AbstractImageWindow(Window window){
		super(window);
		init();
	}

	public AbstractImageWindow(){
		super();
		init();
	}

	/**
	 * 初始化面板背景
	 */
	private void init(){
		// 获取图片
		ImageIcon icon = getBackgroundIcon();
		Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());

		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);    	// 窗体关闭策略（关闭后挂起）
		setSize(size);											// 窗体大小和图片一致
		setUndecorated(true);									// 窗体无边框
		setBackground(new Color(0, 0, 0, 0));		// 窗体背景色为透明（alpha = 0)

		// 设置背景
		JLabel background = new JLabel(icon);
		background.setSize(background.getPreferredSize());
		JLayeredPane.putLayer(background, Integer.MIN_VALUE);	// 将背景放在最底层
		getLayeredPane().add(background);
	}

	public abstract ImageIcon getBackgroundIcon();
}
