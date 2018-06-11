package com.project.RobotBot;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class AntiAfkBot implements Runnable {
	private Thread t;
	private String threadName;
	private Robot robot;
	private Keyboard keyboard;
	private volatile boolean running = true;

	AntiAfkBot(String name, Robot robot, Keyboard keyboard) {
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
				Random r = new Random();
				Thread.sleep(r.nextInt(1000) + 80000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				Thread.sleep(r.nextInt(100) + 700);
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
				Thread.sleep(r.nextInt(100) + 900);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(r.nextInt(1000) + 80000);
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
