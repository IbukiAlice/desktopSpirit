package com.ds;

import com.ds.project.frame.MainFrame;
import com.ds.project.frame.TestFrame;

import java.lang.management.ManagementFactory;

public class Application {

	public static void main(String[] args) {
//		System.out.println(ManagementFactory.getRuntimeMXBean().getName());

		new MainFrame();
//		new TestFrame();
	}
}
