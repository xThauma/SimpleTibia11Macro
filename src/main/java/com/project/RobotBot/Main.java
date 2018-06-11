package com.project.RobotBot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalTime;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	private static Robot robot;
	private static Keyboard keyboard;
	private static HealingBot healingBot;
	private static MessageBot messageBot;
	private static AutoAttackBot autoAttackBot;
	private static AutoMoveBot autoMoveBot;
	private static AutoHotkey1 autoHotkey1;
	private static AutoHotkey2 autoHotkey2;
	private static AutoHotkey3 autoHotkey3;
	private static AutoHotkey4 autoHotkey4;
	private static Stage window;
	private static Scene scene;
	private static HBox hBoxCenter;
	private static HBox hBoxTop;
	private static HBox hBoxBottom;
	private static BorderPane borderPane;
	// private static Process process;
	private static AntiAfkBot antiAfkBot;
	private static AutoLoot autoLoot;
	private static int moveUp = 0;

	private static TextField fromPosxTf;
	private static TextField fromPosyTf;
	private static TextField toPosxTf;
	private static TextField toPosyTf;

	private final static int maxWidth = 100;

	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle("BOT");

		borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10, 10, 10, 10)); // (top/right/bottom/left)

		hBoxTop = new HBox();
		hBoxTop.setPadding(new Insets(10, 10, 10, 10)); // (top/right/bottom/left)
		hBoxTop.setSpacing(5);

		hBoxCenter = new HBox();
		hBoxCenter.setPadding(new Insets(10, 10, 10, 10)); // (top/right/bottom/left)
		hBoxCenter.setSpacing(5);

		hBoxBottom = new HBox();
		hBoxBottom.setPadding(new Insets(10, 10, 10, 10)); // (top/right/bottom/left)
		hBoxBottom.setSpacing(5);

		// PosRefresh posRefresh = new PosRefresh("Refresh Pos", robot,
		// keyboard, l1, l2);
		// posRefresh.start();

		Button antiAfkButton = new Button("Start anti-afk");
		antiAfkButton.setMinWidth(maxWidth);
		antiAfkButton.setOnAction(e -> {
			try {
				startAntiAfk();
			} catch (AWTException | InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		Button stopAntiAfk = new Button("Stop anti-afk");
		stopAntiAfk.setMinWidth(maxWidth);
		stopAntiAfk.setOnAction(e -> {
			antiAfkBot.stop();
		});
		
		Button startAutoLoot = new Button("Start auto-loot");
		startAutoLoot.setMinWidth(maxWidth);
		startAutoLoot.setOnAction(e -> {
			try {
				startAutoLoot();
			} catch (AWTException | InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		Button stopAutoLoot = new Button("Stop auto-loot");
		stopAntiAfk.setMinWidth(maxWidth);
		stopAntiAfk.setOnAction(e -> {
			autoLoot.stop();
		});

		Button autoMoveButton = new Button("Start auto move");
		autoMoveButton.setMinWidth(maxWidth);
		autoMoveButton.setOnAction(e -> {
			try {
				startAutoMove();
			} catch (AWTException | InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		Button stopAutoMove = new Button("Stop auto move");
		stopAutoMove.setMinWidth(maxWidth);
		stopAutoMove.setOnAction(e -> {
			autoMoveBot.stop();
		});

		Label labelPos = new Label();
		labelPos.setMinWidth(maxWidth);

		fromPosxTf = new TextField();
		fromPosxTf.setPromptText("From pos x");

		fromPosyTf = new TextField();
		fromPosyTf.setPromptText("From pos y");

		toPosxTf = new TextField();
		toPosxTf.setPromptText("To pos x");

		toPosyTf = new TextField();
		toPosyTf.setPromptText("To pos y");

		Button buttonPos = new Button("Drag mouse to get current position");
		buttonPos.setOnMouseReleased(e -> {
			Point p = MouseInfo.getPointerInfo().getLocation();
			int x = p.x;
			int y = p.y;
			Color currentColor = robot.getPixelColor(x, y);
			labelPos.setText("Position: [x: +" + x + ", y: " + y + "],  Colour: [RGB: " + currentColor.getRGB() + "]");
		});

		hBoxCenter.getChildren().addAll(antiAfkButton, stopAntiAfk, autoMoveButton, stopAutoMove, startAutoLoot, stopAutoLoot);

		hBoxTop.getChildren().addAll(fromPosxTf, fromPosyTf, toPosxTf, toPosyTf);

		hBoxBottom.getChildren().addAll(buttonPos, labelPos);

		borderPane.setCenter(hBoxCenter);
		borderPane.setTop(hBoxTop);
		borderPane.setBottom(hBoxBottom);

		robot = new Robot();

		scene = new Scene(borderPane);

		window.setAlwaysOnTop(true);
		window.setX(0);
		window.setY(0);
		window.setOnCloseRequest(e ->

		{
			window.close();
			System.exit(0);
		});
		window.setScene(scene);
		window.show();

	}

	public static void moveUp() throws InterruptedException {
		while (moveUp == 0) {
			robot.keyPress(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_UP);
			Thread.sleep(100);
		}
	}

	public static void stopMoveUp() throws InterruptedException {
		moveUp = 1;
	}

	public static void startAutoMove() throws AWTException, InterruptedException {
		robot = new Robot();
		keyboard = new Keyboard();
		String fromX = fromPosxTf.getText().toString();
		String fromY = fromPosyTf.getText().toString();
		String toX = toPosxTf.getText().toString();
		String toY = toPosyTf.getText().toString();
		autoMoveBot = new AutoMoveBot("Auto move bot", robot, keyboard, fromX, fromY, toX, toY);
		autoMoveBot.start();
	}

	public static void startAntiAfk() throws AWTException, InterruptedException {
		robot = new Robot();
		keyboard = new Keyboard();
		antiAfkBot = new AntiAfkBot("Anti afk bot", robot, keyboard);
		antiAfkBot.start();
	}
	
	public static void startAutoLoot() throws AWTException, InterruptedException {
		robot = new Robot();
		keyboard = new Keyboard();
		autoLoot = new AutoLoot("auto loot boot", robot, keyboard);
		autoLoot.start();
	}

	public static void startBot() throws AWTException, InterruptedException {
		robot = new Robot();
		keyboard = new Keyboard();
		antiAfkBot = new AntiAfkBot("Anti afk bot", robot, keyboard);
		healingBot = new HealingBot("Healing bot", robot, keyboard);
		autoAttackBot = new AutoAttackBot("Attacking robot", robot, keyboard);
		messageBot = new MessageBot("Message check bot", robot, keyboard);

		autoHotkey1 = new AutoHotkey1("Hotkey1", robot, keyboard);
		autoHotkey2 = new AutoHotkey2("Hotkey2", robot, keyboard);
		autoHotkey3 = new AutoHotkey3("Hotkey3", robot, keyboard);
		autoHotkey4 = new AutoHotkey4("Hotkey4", robot, keyboard);

		// mnie
		// singleLeftClick(robot);
		Thread.sleep(100);
		autoHotkey1.start();
		// autoHotkey2.start();
		autoHotkey3.start();
		autoHotkey4.start();
		autoAttackBot.start();
		// healingBot.start();
		// messageBot.start();
		// antiAfkBot.start();
		// dbWalking(robot, keyboard);
		// startGame(robot, keyboard, 22);
	}

	public static void dbWalking(Robot robot, Keyboard keyboard) throws InterruptedException {
		while (true) {
			robot.keyPress(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_UP);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_DOWN);
		}
		// keyboard.type("power down");
	}

	public static boolean getTime(int n) {
		LocalTime localTime = LocalTime.now();
		System.out.println(localTime.getMinute() + ":" + localTime.getSecond());
		if (n == localTime.getMinute())
			return true;
		else
			return false;
	}

	public static void startGame(Robot robot, Keyboard keyboard, int time) throws InterruptedException {
		while (true) {
			System.out.print("Checking time... ");
			if (getTime(time)) {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("C://Users//Master//Downloads//ipchanger.exe");
				} catch (IOException io) {
					io.printStackTrace();
				}
				System.out.print("Starting Tibia.");
				logIn(robot, keyboard, "8148203", "kozakoza3");
			}
			Thread.sleep(10000);
		}

	}

	public static void dubleLeftClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void singleLeftClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void singleRightClick(Robot robot) {
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}

	public static void logIn(Robot robot, Keyboard keyboard, String NameAcc, String PassAcc) {
		robot.delay(2000);
		keyboard.type("sky-war.org");
		robot.delay(500);
		robot.mouseMove(888, 541);
		singleLeftClick(robot);
		robot.delay(3000);
		robot.mouseMove(85, 833);
		singleLeftClick(robot);
		robot.delay(500);
		keyboard.type(NameAcc);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.delay(500);
		keyboard.type(PassAcc);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1500);
		robot.mouseMove(1211, 578);
		singleLeftClick(robot);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(500);
		robot.mouseMove(865, 381);
		singleRightClick(robot);
		robot.delay(700);
		robot.mouseMove(1898, 5);
		singleLeftClick(robot);
	}

}
