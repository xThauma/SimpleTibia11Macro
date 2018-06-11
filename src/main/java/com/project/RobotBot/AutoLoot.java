package com.project.RobotBot;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class AutoLoot implements Runnable {
	private Thread t;
	private String threadName;
	private Robot robot;
	private Keyboard keyboard;
	private volatile boolean running = true;

	AutoLoot(String name, Robot robot, Keyboard keyboard) {
		threadName = name;
		this.robot = robot;
		this.setKeyboard(keyboard);
		System.out.println("Creating " + threadName);
	}

	public static void singleLeftClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void singleRightClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}

	public void run() {
		while (running) {
			try {
				Color hpColour = robot.getPixelColor(1769, 1017);
				int hpRGB = -12898513;
				if (hpColour.getRGB() != hpRGB) {
					for (int i = 0; i < 100; i++) {
						Thread.sleep(10);
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.mouseMove(1769, 1817);
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						Thread.sleep(10);
						robot.mouseMove(1775, 925);
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						robot.keyRelease(KeyEvent.VK_CONTROL);
					}
				}

			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_F8)
			System.exit(0);
	}

	public void stop() {
		running = false;
		Thread.currentThread().interrupt();
	}

	public void start() {
		System.out.println("Starting " + threadName);
		running = true;
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}
}
