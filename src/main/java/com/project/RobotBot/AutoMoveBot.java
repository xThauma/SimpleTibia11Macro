package com.project.RobotBot;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class AutoMoveBot implements Runnable {
	private Thread t;
	private String threadName;
	private Robot robot;
	private Keyboard keyboard;
	private volatile boolean running = true;
	Integer fromX, fromY, toX, toY;

	AutoMoveBot(String name, Robot robot, Keyboard keyboard, String fromX, String fromY, String toX, String toY) {
		threadName = name;
		this.robot = robot;
		this.setKeyboard(keyboard);
		this.fromX = Integer.valueOf(fromX);
		this.fromY = Integer.valueOf(fromY);
		this.toX = Integer.valueOf(toX);
		this.toY = Integer.valueOf(toY);
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
				Thread.sleep(r.nextInt(100) + 110000);
				robot.mouseMove(fromX, fromY);
				Thread.sleep(r.nextInt(100) + 2000);
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				Thread.sleep(r.nextInt(100) + 2000);
				robot.mouseMove(toX, toY);
				Thread.sleep(r.nextInt(100) + 2000);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				Thread.sleep(r.nextInt(100) + 2000);
				robot.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(r.nextInt(100) + 200);
				robot.keyRelease(KeyEvent.VK_ENTER);
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
