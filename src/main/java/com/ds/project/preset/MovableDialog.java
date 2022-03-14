package com.ds.project.preset;

/**
 * 可跟随主窗口移动的窗口
 * @author IbukiAlice
 */
public interface MovableDialog {

	/**
	 * 根据主窗口的位置信息，设置自身的位置
	 * @param force	是否强制调整位置
	 *              如为true，则无论窗体是否可见都更新位置
	 *              如为false，则仅窗体可见时更新位置
	 */
	void alterLocation(boolean force);

	/**
	 * 根据主窗口的位置信息，设置自身的位置
	 * 则仅窗体可见时更新位置
	 */
	default void alterLocation() {
		alterLocation(false);
	}
}
