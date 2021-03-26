package pl.kielce.tu.arkanoid;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application implements EventHandler<KeyEvent>{

	public static final String APP_NAME = "Arkanoid";
	
	Ball ball;
	Shelf shelf;
	Block[][] blocks;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle(APP_NAME);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Alt+f4"));
		
		Values.gameState = GameState.MENU;
		
		GraphicsSystem.width = Screen.getPrimary().getBounds().getWidth();
		GraphicsSystem.height = Screen.getPrimary().getBounds().getHeight();
		
		Canvas canvas  = new Canvas(GraphicsSystem.width, GraphicsSystem.height);
		GraphicsContext context = canvas.getGraphicsContext2D();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), event->run(context)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		Scene scene = new Scene(new StackPane(canvas));
		
		scene.setOnKeyPressed(this);
		scene.setOnKeyReleased(event -> {
			switch(event.getCode()) {
			case LEFT:
				Values.leftKeyPressed = false;
				break;
			case RIGHT:
				Values.rightKeyPressed = false;
				break;
			default:
				break;
			}
		});
		
		primaryStage.setScene(scene);
		
		createGameObjects();
		
		primaryStage.show();
		timeline.play(); 
	}
	
	private void createGameObjects() {
		ball = new Ball(GraphicsSystem.width/2 - 10, GraphicsSystem.height *0.8, 20, 3, -3);
		GraphicsSystem.ball = ball;
		EventSystem.ball = ball;
		
		shelf = new Shelf(GraphicsSystem.width/2 -100, GraphicsSystem.height*0.9, 200, 7);
		GraphicsSystem.shelf = shelf;
		EventSystem.shelf = shelf;
		
		blocks = new Block[10][6];
		
		// dzielimy górn¹ po³owê ekranu na siatkê, te zmienne to wielkoœæ komórki od siatki
		float distanceX = (float)GraphicsSystem.width / blocks.length;
		float distanceY = (float)GraphicsSystem.height / (2 * blocks[0].length);
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				blocks[i][j] = new Block(
						distanceX * i + distanceX * 0.01,
						distanceY * j + distanceY * 0.01,
						distanceX  - distanceX * 0.02,
						distanceY  - distanceY * 0.04,
						new Color(1.0 - i * (1.0/blocks.length), (float) i * (1.0/blocks.length), (float) (1 - j * (1.0 / blocks[i].length)), 1)
				);
			}
		}
		
	}

	private void run(GraphicsContext context) {
		GraphicsSystem.setBackground(context);
		
		switch(Values.gameState) {
		case GAME:
			GraphicsSystem.showGame(context);
			GraphicsSystem.showBall(context);
			EventSystem.moveBall();
			GraphicsSystem.showShelf(context);
			EventSystem.moveShelf(context);
			GraphicsSystem.showBlocks(context, blocks);
			break;
		case HIGHSCORES:
			break;
		case MENU:
			GraphicsSystem.showMenu(context);
			break;
		case SETTINGS:
			GraphicsSystem.showSettings(context);
			break;
		case QUIT:
			Platform.exit();
			break;
		default:
			break;
		}
	}

	@Override
	public void handle(KeyEvent event) {
		switch(Values.gameState) {
		case GAME:
			EventSystem.handleGameEvent(event);
			break;
		case HIGHSCORES:
			break;
		case MENU:
			EventSystem.handleMenuEvent(event);
			break;
		case QUIT:
			break;
		case SETTINGS:
			EventSystem.handleSettingsEvent(event);
			break;
		default:
			break;
		
		}
		
		if(Values.newGame) {
			Values.gameStarted = false;
			Values.newGame = false;
			ball = new Ball(GraphicsSystem.width/2 - 10, GraphicsSystem.height *0.8, 20, 3, -3);
			GraphicsSystem.ball = ball;
			EventSystem.ball = ball;
			shelf = new Shelf(GraphicsSystem.width/2 -100, GraphicsSystem.height*0.9, 200, 7);
			GraphicsSystem.shelf = shelf;
			EventSystem.shelf = shelf;
		}
		
	}
}
