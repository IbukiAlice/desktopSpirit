package com.ds.project.data;

public class ClockData {
	private int id;

	private long timeMilli;

	private String showDate;

	private String text;

	public static final long DAY_MILLI = 86400000;
	public static final long HOUR_MILLI = 3600000;
	public static final long MINUTE_MILLI = 60000;

	public ClockData(){}

	/**
	 * 通过存储字符串初始化时钟
	 * @param id	id
	 * @param str	存储字符串
	 */
	protected ClockData(int id, String str){
		this.id = id;
		int index = str.indexOf(',');
		resetTime(Long.parseLong(str.substring(0, index)), str.substring(index + 1));
	}

	/**
	 * 判断给定时间毫秒数是否与闹钟记录的毫秒数接近（大于记录时间，但不超过1分钟）
	 * @param timeMilli	给定时间毫秒数
	 * @return	true表示接近
	 */
	public boolean checkTimeClose(long timeMilli){
		long timeDiff = timeMilli % DAY_MILLI - this.timeMilli;
		return timeDiff >= 0 && timeDiff <= MINUTE_MILLI;
	}

	/**
	 * 重置闹钟
	 * @param timeMilli	时间毫秒数
	 * @param text		展示文本
	 */
	public void resetTime(long timeMilli, String text) {
		this.timeMilli = ((timeMilli % DAY_MILLI) / MINUTE_MILLI) * MINUTE_MILLI;
		this.text = text;

		this.showDate = String.format("%02d:%02d",
			this.timeMilli / HOUR_MILLI + 8, (this.timeMilli % HOUR_MILLI) / MINUTE_MILLI);
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getShowDate() {
		return showDate;
	}

	public String getText() {
		return text;
	}

	/** 生成存储用字符串 */
	protected String toStoreString(){
		return timeMilli + "," + text;
	}
}
