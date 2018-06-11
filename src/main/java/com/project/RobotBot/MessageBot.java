package com.project.RobotBot;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MessageBot implements Runnable {
	private Thread t;
	private String threadName;
	private Robot robot;
	private Keyboard keyboard;
	private volatile boolean running = true;

	MessageBot(String name, Robot robot, Keyboard keyboard) {
		threadName = name;
		this.robot = robot;
		this.setKeyboard(keyboard);
	}

	public void run() {
		while (running) {
			try {
				Thread.sleep(100);
				Color defaultColour = robot.getPixelColor(64, 614);
				System.out.println(defaultColour);
			//	System.out.println(pmColour);
				int defaultBlue = 127;
				int defaulGreen = 127;
				int defaulRed = 127;
				String alarm = "alarm.mp3";
				if (defaultColour.getBlue() != defaultBlue && defaultColour.getGreen() != defaulGreen
						&& defaultColour.getRed() != defaulRed) {
					Media hit = new Media(new File(alarm).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(hit);
					mediaPlayer.play();
					Thread.sleep(10000);
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
