package com.ds.project.preset;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * 隐藏的滚动条UI
 * @author IbukiAlice
 */
public class HideScrollBarUI extends BasicScrollBarUI {

	private final static Dimension ZERO_SIZE = new Dimension(0, 0);

	@Override
	public Dimension getPreferredSize(JComponent c) {
		return ZERO_SIZE;
	}

	@Override
	public Dimension getMaximumSize(JComponent c) {
		return ZERO_SIZE;
	}

	@Override
	public Dimension getMinimumSize(JComponent c) {
		return ZERO_SIZE;
	}
}
