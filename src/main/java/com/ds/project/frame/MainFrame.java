package com.ds.project.frame;

import com.ds.project.component.MsgDialog;
import com.ds.project.component.TopEditableDialog;
import com.ds.project.data.ClockData;
import com.ds.project.data.DataSource;
import com.ds.project.preset.DraggableFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 程序主窗体
 *
 * @author IbukiAlice
 */
public class MainFrame extends DraggableFrame {

	public MainFrame() {
		// 设置窗体属性
		setAlwaysOnTop(true);                               // 窗体置顶
		setMouseDraggable(true);                            // 允许鼠标拖动窗口

		// 设置右键弹出关闭菜单
		setRightClickExitPopupMenu();

		// 设置双击弹出顶部编辑面板
		setDoubleClickEditDialog();

		// 设置消息框
		setMsgDialog();

		// 显示窗体
		setVisible(true);
	}

	/**
	 * 结束时关闭定时器
	 */
	@Override
	public void dispose() {
		if(timer != null) {
			timer.stop();
		}

		super.dispose();

		// 输入框用搜狗输入中文时进程不会关闭，因此放一个exit在这里
		System.exit(0);
	}

	/**
	 * 获取背景图片
	 *
	 * @return 背景图片
	 */
	@Override
	public ImageIcon getBackgroundIcon() {
		return new ImageIcon(this.getClass().getResource("/pics/bp.png"));
	}

	/**
	 * 右键弹出关闭菜单
	 */
	private void setRightClickExitPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem closeItem = new JMenuItem("关闭");
		closeItem.addActionListener(event -> dispose());
		popupMenu.add(closeItem);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// 判断右键
				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * 双击弹出顶部编辑窗口
	 */
	private void setDoubleClickEditDialog() {
		TopEditableDialog topDialog = new TopEditableDialog(this);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					topDialog.setVisible(!topDialog.isVisible());
				}
			}
		});

		addFollowDialog(topDialog);
	}

	private boolean showMsgDialog = false;

	private Timer timer;

	/**
	 * 定时器弹出左侧对话框
	 */
	private void setMsgDialog(){
		MsgDialog msgDialog = new MsgDialog(this);
		addFollowDialog(msgDialog);

		timer = new Timer(1000, e -> {
			showMsgDialog = false;

			long curTime = System.currentTimeMillis();

			for (ClockData clock : DataSource.me().getClocks()) {
				if(clock.checkTimeClose(curTime)){
					msgDialog.updateText(clock.getText());
					showMsgDialog = true;
					break;
				}
			}

			boolean isShow = msgDialog.isVisible();
			if(isShow != showMsgDialog){
				msgDialog.setVisible(showMsgDialog);
			}
		});

		timer.start();
	}
}
