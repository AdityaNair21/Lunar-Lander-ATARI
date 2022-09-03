import javafx.scene.image.Image;

public class Flame extends Actor {
	
	public Flame() {
		this.setImage(new Image("file:images/flame5.png"));
		this.setPreserveRatio(false);
		
	}
	
	@Override
	void act(long now) {
		
	}

	public void setSize(double width, double height) {
		this.setFitHeight(height);
		this.setFitWidth(width);
		
	}

}
