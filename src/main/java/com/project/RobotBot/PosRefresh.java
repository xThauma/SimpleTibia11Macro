package com.project.RobotBot;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class PosRefresh implements Runnable {
	private Thread t;
	private String threadName;
	private Keyboard keyboard;
	private volatile boolean running = true;
	Label l1;
	Label l2;

	PosRefresh(String name, Robot robot, Keyboard keyboard, Label l1, Label l2) {
		this.l1=l1;
		this.l2=l2;
		threadName = name;
		this.setKeyboard(keyboard);
	}

	
	
	public void run() {
		while (running) {
			try {
				Point p = MouseInfo.getPointerInfo().getLocation();
				int x = p.x;
				int y = p.y;	
				l1.textProperty().bind(new SimpleIntegerProperty(x).asString());
				l2.textProperty().bind(new SimpleIntegerProperty(y).asString());		
				Thread.sleep(100);
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
