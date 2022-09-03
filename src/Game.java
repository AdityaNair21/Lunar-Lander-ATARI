
/**
*Aditya Nair and Aniket Nandi
*APCS Period 2
*Date:3/18/2019
*Time spent: 4.5 Hours
*Reflection: This lab was great practice on github as our group had to often push, pull, and sometimes sort out merge conflict (although we tried to avoid them).
*We enjoyed this project as it was our first java project we had done as a team, so we were able to work together on the problems. Throught the course of this
*breakout game project, our team ran into multiple errors. One error we had was that we forgot to add the ball, paddle, and brick to the world itself, not just the 
*node world. This prevented our program from executing, and is the reason why we had to resubmit. Another error we ran into was that Aniket was not able to push 
*his code, because although he had made changes and saved them, ecplise showed that nothing was staged and therefore he could not push. To overcome this challenge, 
*he sent Aditya the code on google drive so that Aditya could push it into bitbucket. Finally, a third challenge we faces was getting the ball to interact more 
*naturally with the paddle and bricks. This took a while because it was a bit confusing checking all the edge cases and avoiding the ball from getting stuck anywhere.
*Overall, we learned a lot about github and team projects through this experiment and we are excited to start our game project itself. 
*
* 
*/


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Game extends Application{
	private BorderPane rootNode;
	StartWorld takeoff = new StartWorld();
	ScoreHUD hud;
	Rocket r;
	Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Lunar Landing");
		rootNode = new BorderPane();
		takeoff.setPrefSize(1000, 1000);
		rootNode.setCenter(takeoff);
		scene = new Scene(rootNode, 800, 800, Color.BLACK);
		
		
		
		//Actors for startworld
		r = new Rocket(50);
		takeoff.add(r);
		r.setX(250);
		r.setY(150);
		r.addHitbox();
		
		hud = new ScoreHUD(r);
		takeoff.add(hud);
		hud.initializeHUD();
		
		Land l = new Land();
		takeoff.add(l);
		l.initializeLines();
		l.moveRandom();
		
		Flame f = new Flame();
		f.setX(r.getX());
		f.setY(r.getY());
		takeoff.add(f);
		
		
		stage.setScene(scene);
		stage.show();
		takeoff.start();
		takeoff.requestFocus();
		takeoff.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
			
			
		});
		
		
		takeoff.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				takeoff.addKeyCode(event.getCode());
				if(event.getCode().equals(KeyCode.R)) {
					takeoff.removeAll();
					takeoff.getChildren().removeAll(takeoff.getChildren());
					
					
					//Actors for startworld
					r = new Rocket(50);
					takeoff.add(r);
					r.setX(250);
					r.setY(150);
					r.addHitbox();
					
					ScoreHUD hud2 = new ScoreHUD(r);
					takeoff.add(hud2);
					hud2.initializeHUD();
					
					Land l = new Land();
					takeoff.add(l);
					l.initializeLines();
					l.moveRandom();
					
					Flame f = new Flame();
					f.setX(r.getX());
					f.setY(r.getY());
					takeoff.add(f);
					
					
//					takeoff.start();
//					takeoff.requestFocus();
					
					
				//	stage.setScene(scene);
				}
			}
			
		});
		
		takeoff.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				takeoff.removeKeyCode(event.getCode());
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
