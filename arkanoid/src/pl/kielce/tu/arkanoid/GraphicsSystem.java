package pl.kielce.tu.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GraphicsSystem {
	static double width;
	static double height;
	
	static final String[] MENU_ITEMS = {"New game", "Settings", "Highscores", "Quit"};
	static final String[] SETTINGS_ITEMS = {"Difficulty level", "Sound"};
	static final String[] DIFFICULTY_LEVELS = {"Easy", "Medium", "Hard"};
	static final String[] SOUND_STATE = {"Yes", "No"};
	
	static final Color greyColor = new Color(0.4, 0.4, 0.5, 1);

	static Ball ball;
	static Shelf shelf;
	static Block[][] blocks;
		
	public static void setBackground(GraphicsContext context) {
		context.setFill(Color.BLACK);
		context.fillRect(0, 0, width, height);
	}
	
	public static void showMenu(GraphicsContext context) {		
		for(int i = 0; i < MENU_ITEMS.length; i++) {
			if(i == Values.currentMenuOption) {
				context.setFill(Color.WHITE);
			}
			else {
				context.setFill(greyColor);
			}
			context.setFont(Font.font(40));
			context.setTextAlign(TextAlignment.CENTER);
			context.fillText(MENU_ITEMS[i], width/2, height*0.3 + height*i*0.15);
		}
		
		
	}

	public static void showSettings(GraphicsContext context) {
		context.setFont(Font.font(40));
		context.setTextAlign(TextAlignment.LEFT);
		for(int i = 0; i < SETTINGS_ITEMS.length; i++) {
			if(i == Values.currentSettingsOption) {
				context.setFill(Color.WHITE);
			}
			else {
				context.setFill(greyColor);
			}
			
			context.fillText(SETTINGS_ITEMS[i], width * 0.25, height*0.35 + height*i*0.15);
			
			if(i == 0) {
				context.fillText(DIFFICULTY_LEVELS[Values.currentDifficultyLevel], width * 0.65, height*0.35 + height*i*0.15);
			}
			if(i == 1) {
				context.fillText(Values.soundEnabled ? SOUND_STATE[0] : SOUND_STATE[1], width * 0.65, height*0.35 + height*i*0.15);
			}
		}
	}

	public static void showGame(GraphicsContext context) {
		if(!Values.gameStarted) return;
		
	}
	
	public static void showBall(GraphicsContext context) {
		context.setFill(Color.WHITE);
		context.fillOval(ball.xPos, ball.yPos, ball.diameter, ball.diameter);
		
	}
	
	public static void showShelf(GraphicsContext context) {
		context.setFill(Color.WHITE);
		context.fillRect(shelf.xPos, shelf.yPos, shelf.width, shelf.height);
	}

	public static void showBlocks(GraphicsContext context) {
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				if(!blocks[i][j].getExists()) continue;
				context.setFill(blocks[i][j].color);
				context.fillRect(blocks[i][j].x1, blocks[i][j].y1, blocks[i][j].width, blocks[i][j].height);
			}
		}
	}

	
}
