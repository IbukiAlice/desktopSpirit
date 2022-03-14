package com.ds.project.preset;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 可拖动窗口
 *
 * @author IbukiAlice
 */
public abstract class DraggableFrame extends AbstractImageWindow {

	/**
	 * 窗口是否可拖动
	 */
	private boolean draggable = false;

	/**
	 * 鼠标拖动适配器
	 */
	private MouseDragAdapter dragAdapter;

	/**
	 * 需要跟随拖动的其它窗口
	 */
	private final List<FollowDialog> followDialogList = new ArrayList<>();

	/**
	 * 设置窗口是否可用鼠标拖动
	 *
	 * @param flag true表示设置窗口为可拖动，false设置为不可拖动，默认为false
	 */
	public void setMouseDraggable(boolean flag) {
		if (this.draggable == flag) return;

		if (flag) {
			if (dragAdapter == null) {
				dragAdapter = new MouseDragAdapter();
			}
			addMouseDragListener(dragAdapter);
		} else if (dragAdapter != null) {
			removeMouseDragListener(dragAdapter);
		}

		this.draggable = flag;
	}

	private void addMouseDragListener(MouseDragAdapter adapter) {
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
	}

	private void removeMouseDragListener(MouseDragAdapter adapter) {
		removeMouseListener(adapter);
		removeMouseMotionListener(adapter);
	}

	/**
	 * 鼠标拖动适配器
	 */
	private class MouseDragAdapter extends MouseAdapter {

		private boolean isDragging;
		final private Point oldPoint = new Point();

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				oldPoint.setLocation(e.getX(), e.getY());
				isDragging = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				isDragging = false;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (isDragging) {
				Component c = e.getComponent();
				c.setLocation(c.getX() - oldPoint.x + e.getX(), c.getY() - oldPoint.y + e.getY());

				// 同步刷新跟随窗口
				followDialogList.forEach(MovableDialog::alterLocation);
			}
		}
	}

	/**
	 * 添加跟随窗口
	 * @param d	跟随窗口
	 */
	public void addFollowDialog(FollowDialog d){
		followDialogList.add(d);
	}

	/**
	 * 删除跟随窗口
	 * @param d	跟随窗口
	 */
	public void removeFollowDialog(FollowDialog d){
		followDialogList.remove(d);
	}
}
