import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreHUD extends Actor{
	private final int FONT_SIZE = 20;
	private final int FUEL = 1000;
	private int score = 0;
	private int time = 0;
	private int fuel, altitude, verticalSpeed, horizontalSpeed;
	private Text[] HUD;
	Text instructions;
	
	public ScoreHUD(Rocket r) {
		HUD = new Text[6];
		
		fuel = r.getFuel();
		altitude = r.getAltitude();
		verticalSpeed = r.getVerticalSpeed();
		horizontalSpeed = r.getHorizontalSpeed();
	}
	
	public void initializeHUD() {
		//Create Text Objects inside ScoreHUD
		for (int i = 0; i < 6; i++) {
			HUD[i] = new Text();
			HUD[i].setFont(new Font("TimesRoman", FONT_SIZE));
			HUD[i].setFill(Color.ANTIQUEWHITE);
		}
		updateDisplay();
		
		instructions = new Text();
		instructions.setFont(new Font("TimesRoman", FONT_SIZE/1.5));
		instructions.setFill(Color.ANTIQUEWHITE);
		instructions.setX(200);
		instructions.setY(45);
		instructions.setText("Use Left and Right Arrow Keys to Move,\n Up Arrow Key to use Boosters,\n Spacebar to apply full Throttle to the Boosters,\n R to Play Again or Reset");
		this.getWorld().getChildren().add(instructions);
		//Set Locations
		for (int i = 0; i < 6; i++) {
			this.getWorld().getChildren().add(HUD[i]);
			if (i < 3) {
				HUD[i].setX(FONT_SIZE);
				HUD[i].setY(FONT_SIZE * (i + 1));
			}  else if (i >= 3) {
				HUD[i].setX(500 - FONT_SIZE);
				HUD[i].setY(FONT_SIZE * (6 - i));
			}
		}
	}
	
	
	
	public void setValues(int fuel, int altitude, int verticalSpeed, int horizontalSpeed, int score) {
		this.fuel = fuel;
		this.altitude = altitude;
		this.verticalSpeed = verticalSpeed;
		this.horizontalSpeed = horizontalSpeed;
		this.score = score;
		
		updateDisplay();
	}
	
	public void updateDisplay() {
		//Score 
		HUD[0].setText("SCORE   " + score);
		//Time
		HUD[1].setText("TIME   " + time);
		//Fuel
		HUD[2].setText("FUEL   " + fuel);
		//Altitude
		HUD[3].setText("ALTITUDE   " + altitude);
		//Horizontal Speed
		HUD[4].setText("HORIZONTAL SPEED   " + horizontalSpeed);
		//Vertical Speed
		HUD[5].setText("VERTICAL SPEED   " + verticalSpeed);
	}

	@Override
	void act(long now) {
		if(this.getWorld().isKeyDown(KeyCode.I)) {
			instructions.setText("Use Left and Right Arrow Keys to Turn,\nUp Arrow Key to use Boosters,\nSpacebar to Apply Full Throttle to the Boosters,\nAnd 'R' to Play Again or Reset");
			instructions.setFont(new Font("TimesRoman", FONT_SIZE/1.5));
			instructions.setX(170);
			instructions.setY(FONT_SIZE);
		} else {
			instructions.setText("Press 'I' for Instructions");
			instructions.setFont(new Font("TimesRoman", FONT_SIZE));
			instructions.setX(200);
			instructions.setY(45);
		}
	}
}




