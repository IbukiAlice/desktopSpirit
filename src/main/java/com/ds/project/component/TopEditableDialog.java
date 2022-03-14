package com.ds.project.component;

import com.ds.project.data.ClockData;
import com.ds.project.data.DataSource;
import com.ds.project.layout.VerticalFlowLayout;
import com.ds.project.preset.FollowDialog;
import com.ds.project.preset.HideScrollBarUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 顶部可编辑弹窗
 * @author IbukiAlice
 */
public class TopEditableDialog extends FollowDialog {

	/** 展示面板 */
	private JPanel panel;

	public TopEditableDialog(Window window){
		super(window);

		// 设置主面板为滚动面板
		JScrollPane scrollPane = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);						// 禁用水平滚动条
		scrollPane.getVerticalScrollBar().setUI(new HideScrollBarUI());				// 隐藏垂直滚动条
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);						// 鼠标滚动速度
		scrollPane.setOpaque(false);												// 面板透明
		scrollPane.getViewport().setOpaque(false);									// 面板透明
		setContentPane(scrollPane);

		// 设置展示面板
		panel = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.TOP));	// 垂直流布局
		panel.setOpaque(false);														// 面板透明
		panel.setSize(getSize());
		scrollPane.setViewportView(panel);

		// 添加已有的闹钟栏
		for (ClockData clock : DataSource.me().getClocks()) {
			panel.add(new ClockItemPanel(clock.getId(), this));
		}

		// 添加"添加"按钮（保持在最底部）
		JButton addButton = new JButton(new ImageIcon(this.getClass().getResource("/pics/button/add.png")));
		addButton.setPreferredSize(new Dimension(getWidth(), 25));
		addButton.setContentAreaFilled(false);
		addButton.setBorderPainted(false);
		addButton.addActionListener(e -> {
			ClockData clockData = DataSource.me().newClock();
			ClockItemPanel clockItemPanel = new ClockItemPanel(clockData.getId(), this);
			panel.add(clockItemPanel, panel.getComponentCount() - 1);		// 保证添加按钮在最底部
			panel.updateUI();
		});
		panel.add(addButton);
	}

	/**
	 * 获取背景图片
	 * @return	背景图片
	 */
	@Override
	public ImageIcon getBackgroundIcon(){
		return new ImageIcon(this.getClass().getResource("/pics/edit.png"));
	}

	/**
	 * 根据主窗口的位置信息，设置自身的位置
	 * @param force	是否强制调整位置
	 *              如为true，则无论窗体是否可见都更新位置
	 *              如为false，则仅窗体可见时更新位置
	 */
	@Override
	public void alterLocation(boolean force) {
		if(!force && !isVisible()) return;

		// 获取主窗口位置信息
		Rectangle r = getOwner().getBounds();

		// 保持在主窗口的顶部居中位置
		int newX = r.x + (r.width - getWidth()) / 2;
		int newY = r.y - getHeight();

		setLocation(newX, newY);
	}

	/** 删除指定时间项，更新其它各时间项数据 */
	public void removeSelfAndUpdateOtherClockItemPanel(ClockItemPanel clockItemPanel){
		panel.remove(clockItemPanel);

		List<ClockData> clockList = DataSource.me().getClocks();
		int i = 0;
		for (Component c : panel.getComponents()) {
			if(c instanceof ClockItemPanel){
				((ClockItemPanel) c).setInfo(clockList.get(i).getId());
				++i;
			}
		}
		panel.updateUI();
	}
}
