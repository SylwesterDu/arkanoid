package pl.kielce.tu.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EventSystem {
	
	static Ball ball = null;
	static Shelf shelf = null;
	
	public static void handleMenuEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.DOWN) {
			if(Values.currentMenuOption < 3) Values.currentMenuOption++;
		}
		else if(event.getCode() == KeyCode.UP) {
			if(Values.currentMenuOption >0) Values.currentMenuOption--;
		}
		else if(event.getCode() == KeyCode.ENTER) {
			if(Values.currentMenuOption == 0) {
				Values.newGame = true;
				Values.gameState = GameState.GAME; 
			}
			if(Values.currentMenuOption == 1) Values.gameState = GameState.SETTINGS; 
			if(Values.currentMenuOption == 2) Values.gameState = GameState.HIGHSCORES; 
			if(Values.currentMenuOption == 3) Values.gameState = GameState.QUIT; 
		}
	}

	public static void handleSettingsEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.DOWN) {
			if(Values.currentSettingsOption < 1) Values.currentSettingsOption++;
		}
		else if(event.getCode() == KeyCode.UP) {
			if(Values.currentSettingsOption > 0) Values.currentSettingsOption--;
		}
		
		switch(Values.currentSettingsOption) {
		case 0:
			if(event.getCode() == KeyCode.RIGHT) {
				if(Values.currentDifficultyLevel < 2) Values.currentDifficultyLevel++;
			}
			if(event.getCode() == KeyCode.LEFT) {
				if(Values.currentDifficultyLevel > 0) Values.currentDifficultyLevel--;
			}
			break;
		case 1:
			if(event.getCode() == KeyCode.RIGHT) Values.soundEnabled = true;
			else if(event.getCode() == KeyCode.LEFT) Values.soundEnabled = false;
			break;
			
		default:
			break;
		}
	}

	public static void handleGameEvent(KeyEvent event) {
		if(event.getCode() == KeyCode.ESCAPE) {
			Values.gameState = GameState.MENU;
			return;
		}
		
		if(event.getCode() == KeyCode.SPACE) {
			Values.gameStarted = true;
		}
		
		if(!Values.gameStarted) return;
		
		switch(event.getCode()) {
		case LEFT:
			Values.direction = MoveDirection.LEFT;
			Values.leftKeyPressed = true;
			break;
		case RIGHT:
			Values.direction = MoveDirection.RIGHT;
			Values.rightKeyPressed = true;
			break;
		default:
			break;
		
		}
	}
	

	public static void moveBall() {
		if(!Values.gameStarted) return;
		checkIfHitOnBorder();
		checkIfHitOnShelf();
		
		checkIfFailed();
	
		ball.xPos += ball.xMovement;
		ball.yPos += ball.yMovement;
		
	}

	private static void checkIfHitOnBorder() {
		if(ball.xPos + 20 > GraphicsSystem.width && ball.yPos < 10) { // uderzenie w prawy górny róg
			ball.xMovement = -ball.xMovement;
			ball.yMovement = -ball.yMovement;
		}
		else if(ball.xPos < 0 && ball.yPos < 10) { // uderzenie w lewy górny róg
			ball.xMovement = -ball.xMovement;
			ball.yMovement = -ball.yMovement;
		}
		else if(ball.xPos + 20 > GraphicsSystem.width) { // uderzenie w praw¹ krawêdŸ
			ball.xMovement = -ball.xMovement;
		}
		else if(ball.xPos < 0) { // uderzenie w lew¹ krawêdŸ
			ball.xMovement = -ball.xMovement;
		}
		else if(ball.yPos < 10) { // uderzenie w górn¹ krawêdŸ
			ball.yMovement = -ball.yMovement;
		}
	}
	
	private static void checkIfHitOnShelf() { // wspolrzedne poziome nie zgadzaj¹ siê
		if(ball.xPos + ball.diameter/2 < shelf.xPos
				|| ball.xPos - ball.diameter/2 > shelf.xPos + shelf.width) {
			return;
		}
		
		if(ball.yPos + ball.diameter > shelf.yPos + ball.yMovement) { // uderzenie w róg deski, przegrana
			return;
		}
		
		
		if(ball.yPos + ball.diameter >= shelf.yPos
				//&& ball.xPos + ball.diameter + ball.yMovement >= shelf.yPos
				) { // uderzenie w deskê
			//ball.yMovement = -ball.yMovement;
			getNewMovement();
		}
	}
	
	private static void checkIfFailed() {
		if(ball.yPos >= GraphicsSystem.height){ // pi³ka poni¿ej deski
			Values.gameState = GameState.MENU;
		}
	}
	
	private static void getNewMovement() {
		double shelfCenter = (shelf.xPos + shelf.width/2);
		double ballCenter = ball.xPos + ball.diameter/2;
		double ballDistanceFromCenter = ballCenter - shelfCenter;
		double distance = ballDistanceFromCenter / shelf.width;
		ball.xMovement = Math.atan(distance) * ball.ballSpeed;
		ball.yMovement = -Math.sqrt(Math.pow(ball.ballSpeed, 2) - Math.pow(ball.xMovement, 2));
		
	}
	
	public static void moveShelf(GraphicsContext context) {
		
		if(Values.direction == MoveDirection.LEFT && Values.leftKeyPressed) {
			if(shelf.xPos <= 0) return;
			shelf.xPos -= shelf.movementSpeed;
		}
		else if(Values.direction == MoveDirection.RIGHT && Values.rightKeyPressed) {
			if(shelf.xPos >= GraphicsSystem.width - shelf.width) return;
			shelf.xPos += shelf.movementSpeed;
		}
		
	}

	
}
