package com.ds.project.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataSource {

	private List<ClockData> clocks;

	private static DataSource me = new DataSource();

	private DataSource() {
		file = new File(System.getProperty("user.dir") + "/db.dat");
		readData();
	}

	public static DataSource me() {
		return me;
	}

	/**
	 * 获取闹钟记录列表
	 */
	public List<ClockData> getClocks() {
		return Collections.unmodifiableList(clocks);
	}

	public int getClockCount(){
		return clocks.size();
	}

	public ClockData getClock(int id){
		return clocks.get(id);
	}

	public void resetClock(int id, long timeMilli, String text){
		clocks.get(id).resetTime(timeMilli, text);
		overwriteData();
	}

	public void newClock(){
		clocks.add(new ClockData());
		overwriteData();
	}

	public void removeClock(int id){
		clocks.remove(id);
		overwriteData();
	}

	// 文件读写相关方法
	private final File file;

	/** 覆盖本地数据 */
	private void overwriteData() {
		String storeStr = clocks.stream().filter(o -> o.getShowDate() != null).map(ClockData::toStoreString).
			reduce((sum, o) -> sum + '\n' + o).orElse(null);

		try {
			// 检测文件名称是否被占用
			if (file.exists()) {
				if (!file.isFile()) {
					throw new IOException("数据存储文件'" + file.getAbsolutePath()
						+ "'创建异常，请检查文件夹是否有同名文件/文件夹");
				}
			}

			if (storeStr == null) return;

			FileOutputStream out = new FileOutputStream(file);
			out.write(storeStr.getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 读取本地数据 */
	private void readData(){
		String data = null;
		if(file.exists() && file.isFile()){
			try {
				FileInputStream in = new FileInputStream(file);

				byte[] bytes = new byte[in.available()];
				int start = 0;
				int len;
				while((len = in.read(bytes, start, bytes.length - start)) > 0){
					start += len;
				}

				data = new String(bytes);

			} catch (IOException e){
				e.printStackTrace();
			}
		}

		clocks = new ArrayList<>();
		if(data != null){
			for (String s : data.split("\\n")) {
				clocks.add(new ClockData(s));
			}
		}
	}
}
