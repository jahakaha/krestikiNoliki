package TicTacToeBot;

	import java.util.ArrayList;

	import java.util.List;
	import java.util.Random;

	import javafx.animation.KeyFrame;
	import javafx.animation.KeyValue;
	import javafx.animation.Timeline;
	import javafx.application.Application;
	import javafx.geometry.Pos;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.input.MouseButton;
	import javafx.scene.layout.Pane;
	import javafx.scene.layout.StackPane;
	import javafx.scene.paint.Color;
	import javafx.scene.shape.Line;
	import javafx.scene.shape.Rectangle;
	import javafx.scene.text.Font;
	import javafx.scene.text.Text;
	import javafx.stage.Stage;
	import javafx.util.Duration;

public class TicTacToeBotApp extends Application{
		
		private boolean playable = true;
		private boolean turnX = true;
		private Tile[][] board = new Tile[3][3];
		private List<Combo> combos = new ArrayList<>();
		
		private Pane root = new Pane();
		
		private Parent createContent() {
			
			root.setPrefSize(600, 600);	
			for(int i = 0; i < 3; i++) {
				 for(int j = 0; j < 3; j++) {
					 Tile tile = new Tile();
					 tile.setTranslateX(i*200);
					 tile.setTranslateY(j*200);
					 
					 root.getChildren().add(tile);
				 
					 board[j][i] = tile;
				 }
			}
			//horizontal
			for(int y = 0; y < 3; y++) {
				combos.add(new Combo(board[0][y], board[1][y], board[2][y] ));
			}
			
			//vertical
			for(int x = 0; x < 3; x++) {
				combos.add(new Combo(board[x][0], board[x][1], board[x][2] ));
			}	
			
			//diagonal
				combos.add(new Combo(board[0][0], board[1][1], board[2][2] ));
				combos.add(new Combo(board[2][0], board[1][1], board[0][2] ));
			
			return root;
		}
		@Override
		public void start(Stage myStage) throws Exception {
			myStage.setScene(new Scene(createContent() ));
			myStage.show();
		}

		private void checkState() {
			for(Combo combo : combos) {
				if(combo.isWin()) {
					playable = false;
					playWinAnimation(combo);
					break;
				}
			}
			
			boolean hasEmpty = false;
			for (int i = 0;i < 3;++i) {
				for (int j = 0;j < 3;++j) {
					if (board[i][j].getValue().isEmpty()) {
						hasEmpty = true;
					}
				}
			}
			if (!hasEmpty) {
				playable = false;
			}
		}
		
		private void playWinAnimation(Combo combo) {
			Line line = new Line(); 
			line.setStartX(combo.tiles[0].getCenterX());
			line.setStartY(combo.tiles[0].getCenterY());
			line.setEndX(combo.tiles[0].getCenterX());
			line.setEndY(combo.tiles[0].getCenterY());
		
			root.getChildren().add(line);
			
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),  
					new KeyValue (line.endXProperty(), combo.tiles[2].getCenterX()),
					new KeyValue (line.endYProperty(), combo.tiles[2].getCenterY() )));
			timeline.play();
		}
		
		private class Combo {
			private Tile[] tiles;
			public Combo(Tile... tiles) {
				this.tiles = tiles;
			}	
			public boolean isWin() {
				if(tiles[0].getValue().isEmpty() )
					return false;

				return tiles[0].getValue().equals(tiles[1].getValue())
						&& tiles[0].getValue().equals(tiles[2].getValue());
			} 
		}
		
		public void moveBot() {
			boolean findOut = false;
			for(int i=0; i<combos.size(); i++) {
				if(combos.get(i).tiles[0].getValue() == "O"
						&& combos.get(i).tiles[0].getValue().equals(combos.get(i).tiles[1].getValue()) 
						&& combos.get(i).tiles[2].getValue().isEmpty()  
						&& findOut == false ) {
					combos.get(i).tiles[2].drawO();
					findOut = true;
				}
				if(combos.get(i).tiles[0].getValue() == "O" 
						&& combos.get(i).tiles[0].getValue().equals(combos.get(i).tiles[2].getValue()) 
						&& combos.get(i).tiles[1].getValue().isEmpty()  
						&& findOut == false ) {
					combos.get(i).tiles[1].drawO();
					findOut = true;
				}
				if(combos.get(i).tiles[2].getValue() == "O" 
						&& combos.get(i).tiles[2].getValue().equals(combos.get(i).tiles[1].getValue()) 
						&& combos.get(i).tiles[0].getValue().isEmpty()  
						&& findOut == false ) {
					combos.get(i).tiles[0].drawO();
					findOut = true;
				}	
			}	
			for(int i=0; i<combos.size(); i++) {
					if(combos.get(i).tiles[0].getValue() == "X" 
							&& combos.get(i).tiles[0].getValue().equals(combos.get(i).tiles[1].getValue()) 
							&& combos.get(i).tiles[2].getValue().isEmpty()  
							&& findOut == false ) {
						combos.get(i).tiles[2].drawO();
						findOut = true;
					}
					if(combos.get(i).tiles[0].getValue() == "X" 
							&& combos.get(i).tiles[0].getValue().equals(combos.get(i).tiles[2].getValue()) 
							&& combos.get(i).tiles[1].getValue().isEmpty()  
							&& findOut == false ) {
						combos.get(i).tiles[1].drawO();
						findOut = true;
					}
					if(combos.get(i).tiles[2].getValue() == "X" 
							&& combos.get(i).tiles[2].getValue().equals(combos.get(i).tiles[1].getValue()) 
							&& combos.get(i).tiles[0].getValue().isEmpty()  
							&& findOut == false ) {
						combos.get(i).tiles[0].drawO();
						findOut = true;
					}
				}	
			
			Random rand = new Random();
			while (!findOut) {
				int randx = rand.nextInt(3);
				int randy = rand.nextInt(3);
				if (board[randx][randy].getValue().isEmpty()) {
					board[randx][randy].drawO();
					findOut = true;
				}
			}
		}
				
	
		
		
		private class Tile extends StackPane{
			private Text text = new Text();
			
			public Tile() {
				Rectangle rect = new Rectangle(200, 200);
				rect.setFill(null);
				rect.setStroke(Color.BLACK);
				
				text.setFont(Font.font(72));
				
				setAlignment(Pos.CENTER);
				getChildren().addAll(rect, text);
			
				setOnMouseClicked(event -> {
					if(!playable)
						return;
					
					if(event.getButton() == MouseButton.PRIMARY) {
						if(!turnX)
							return ;
					
						drawX();
						turnX = false;
						checkState();
						if(playable == true) {
							moveBot();
							checkState();
							turnX = true;
						}
					}
					
				});
			}
			
			public double getCenterX() {
				return getTranslateX() + 100;
			}
			public double getCenterY() {
				return getTranslateY() + 100;
			}
			
			public String getValue() {
				return text.getText();
			}
			
			
			private void drawX() {
				text.setText("X");
			}
			private void drawO() {
				text.setText("O");
			}
			
		}
		
		public static void main(String[] args) {
			launch(args);
		}
	}

