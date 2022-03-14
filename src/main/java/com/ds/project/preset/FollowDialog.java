package com.ds.project.preset;

import javax.swing.*;
import java.awt.*;

/**
 * 可跟随主窗口移动的窗口
 * @author IbukiAlice
 */
public abstract class FollowDialog extends AbstractImageWindow implements MovableDialog {

	public FollowDialog(Window window){
		super(window);
	}

	/**
	 * 重载方法setVisible
	 * 每当置为可见就要刷新位置
	 */
	@Override
	public void setVisible(boolean b) {
		if(b){
			alterLocation(true);
		}
		super.setVisible(b);
	}
}
