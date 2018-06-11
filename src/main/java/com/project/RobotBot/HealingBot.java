package com.project.RobotBot;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class HealingBot implements Runnable {
	private Thread t;
	private String threadName;
	private Robot robot;
	private Keyboard keyboard;
	private volatile boolean running = true;

	HealingBot(String name, Robot robot, Keyboard keyboard) {
		threadName = name;
		this.robot = robot;
		this.setKeyboard(keyboard);
	}

	public static void singleRightClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}

	public static void singleLeftClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public void run() {
		while (running) {
			try {
				Color hpColour = robot.getPixelColor(1847, 153);
				Color hpColourSpell = robot.getPixelColor(1836, 154);
				Color mpColour = robot.getPixelColor(1838, 165);
				int hpRGB = -2404529;
				int hpSpellRGB = -958111;
				int mpRGB = -12369728;
				if (hpColour.getRGB() != hpRGB) {
					robot.keyPress(KeyEvent.VK_F2);
					robot.keyRelease(KeyEvent.VK_F2);
					Thread.sleep(2200);
				} else if (mpColour.getRGB() != mpRGB) {
					robot.keyPress(KeyEvent.VK_F2);
					robot.keyRelease(KeyEvent.VK_F2);
					Thread.sleep(2200);
				}
				if (hpColourSpell.getRGB() != hpSpellRGB) {
					robot.keyPress(KeyEvent.VK_F3);
					robot.keyRelease(KeyEvent.VK_F3);
					Thread.sleep(1050);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
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
