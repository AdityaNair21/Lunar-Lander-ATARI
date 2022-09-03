
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Rocket extends Actor {
	final int MAX_THRUST_TIME = 85;
	final double FUEL_CONSUMTION_CONSTANT = 0.5;
	boolean alive;
	double dx;
	double dy;
	double fuel = 2000;
	int altitude;
	double velocity;
	int VS;
	int HS;
	Circle tester;
	final int SCALEPERCENT = 30;
	double grav = 0.003; // 0.004 //gravity pixel/act^2 pull
	Image flameImage;
	Image transparent;
	Rectangle hitBox;
	int fullThrustCounter;
	boolean fullThrustEnabled;
	int deadCounter;
	int score;

	public Rocket(int altitude) {
		this.setImage(new Image("file:images/lander.png"));
		this.dy = 0;
		this.dx = 1;
		this.score = 0;
		this.setPreserveRatio(true);
		this.setFitHeight(getHeight() / 24);
		hitBox = new Rectangle();
		alive = true;
		velocity = 0;
		this.altitude = altitude;
		VS = 0;
		HS = 0;
		fullThrustCounter = 0;
		fullThrustEnabled = false;
		deadCounter = 0;

	}

	@Override
	void act(long now) {
		if(fuel < 0) {
			fuel = 0;
		}
		Land l = this.getWorld().getObject(Land.class);
		VS = (int) (dy * 25);
		HS = (int) (dx * 25);
		altitude = (int) (l.getLowestY() - this.getY());
		// System.out.println("Rect X: " + hitBox.getBoundsInParent() + " Rect Y: " +
		// hitBox.getY() );
//		if(hitBox.getFill().equals(Color.ANTIQUEWHITE)) {
//			hitBox.setFill(Color.TRANSPARENT);
//		} else {
//			
//			hitBox.setFill(Color.ANTIQUEWHITE);
//		}

//		System.out.println(this.getRotate());
		Flame f = this.getWorld().getObject(Flame.class);
		f.setSize(this.getWidth(), this.getHeight());
		if (alive) {

			

			if (!this.getWorld().getClass().equals(AstroidWorld.class)) {
				if (!fullThrustEnabled) {
					if (this.getWorld().isKeyDown(KeyCode.UP) && fuel > 0) {
						// dx += Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))/64 *
						// Math.cos(Math.toRadians((1 * this.getRotate()) + 90));
						// dy += Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))/64 *
						// Math.sin(Math.toRadians((1 * this.getRotate()) + 90));
						dx += Math.cos(Math.toRadians((1 * this.getRotate()) - 90)) / 200;
						dy += Math.sin(Math.toRadians((1 * this.getRotate()) - 90)) / 200;

						f.setX(this.getX());// - f.getWidth()/2 + this.getWidth()/2);
						f.setY(this.getY());
						f.setRotate(this.getRotate());
//						System.out.println("f reset");

						// f.setX( this.getX() - (this.getWidth() * Math.cos(Math.toRadians((1 *
						// this.getRotate()) + 90))) + this.getWidth()/4);
						// f.setY( this.getY() - (this.getHeight() * Math.sin(Math.toRadians((1 *
						// this.getRotate()) + 90))) - 10);
						// f.setRotate(180 + this.getRotate());

						// f.setY(this.getY() + f.getHeight()/2);
						fuel-=FUEL_CONSUMTION_CONSTANT;
					} else {
						f.setX(1000);
						f.setY(1000);

					}

					dy += grav;

					if (this.getWorld().isKeyDown(KeyCode.LEFT)) {
						if (this.getRotate() - 1 < -90) {
							this.setRotate(-90);
						} else {
							this.setRotate(this.getRotate() - 1);
						}
					}
					if (this.getWorld().isKeyDown(KeyCode.RIGHT)) {
						if (this.getRotate() + 1 > 90) {
							this.setRotate(90);
						} else {
							this.setRotate(this.getRotate() + 1);
						}
					}
					
					if (this.getWorld().isKeyDown(KeyCode.SPACE)) {
						fullThrustEnabled = true;

					}

					this.move(dx, dy);

					// this.move(dx, dy);
					hitBox.setRotate(this.getRotate());
					hitBox.setX(
							this.getX() + (hitBox.getHeight() / 2 * Math.cos(Math.toRadians(this.getRotate() - 90))));
					hitBox.setY(this.getY() + (hitBox.getWidth() / 2 * Math.sin(Math.toRadians(this.getRotate() - 90)))
							+ hitBox.getHeight() / 2);
				} else {
					if(fuel > 0) {
						hitBox.setX(
								this.getX() + (hitBox.getHeight() / 2 * Math.cos(Math.toRadians(this.getRotate() - 90))));
						hitBox.setY(this.getY() + (hitBox.getWidth() / 2 * Math.sin(Math.toRadians(this.getRotate() - 90)))
								+ hitBox.getHeight() / 2);
						hitBox.setRotate(this.getRotate());
						f.setX(this.getX());// - f.getWidth()/2 + this.getWidth()/2);
						f.setY(this.getY());
						f.setRotate(this.getRotate());
						if(this.getRotate() > 0) {
							this.setRotate(this.getRotate() - 2);
						}
						if(this.getRotate() < 0) {
							this.setRotate(this.getRotate() + 2);
						}
						dy -= Math.sin(Math.toRadians((1 * this.getRotate()) + 90)) / 32;
						this.move(dx, dy);
						fuel -= (Math.random() * 15)*FUEL_CONSUMTION_CONSTANT;
						fullThrustCounter++;
						if (fullThrustCounter > MAX_THRUST_TIME) {
							fullThrustCounter = 0;
							fullThrustEnabled = false;
						}
					} else {
						fullThrustEnabled = false;
					}

				}
			} 
			if (l.isInLanding(hitBox)) {
				f.setX(1000);
				f.setY(1000);
				if (Math.abs(this.getRotate()) < 5 && this.getVerticalSpeed() < 15) {
					if(this.getVerticalSpeed() > 10) {
						fuel -= ((Math.random() * 200) + 200);
					} else if(this.getVerticalSpeed() > 5) {
						fuel -= ((Math.random() * 100) + 100);
					}
					alive = false;
					score += (100 * (15 - this.getVerticalSpeed()));
				} else {
					fuel -= ((Math.random() * 300) + 300);
					alive = false;
					this.setImage(new Image("file:images/explosion.gif"));
				}
				
			} else {
				if (l.isIntersectingLand(hitBox)) {
					f.setX(1000);
					f.setY(1000);
					fuel -= ((Math.random() * 400) + 400);
					this.setImage(new Image("file:images/explosion.gif"));
					alive = false;
					
				}
			}
		} else {
			deadCounter++;
			if(deadCounter > 200 && fuel > 0) {
				this.dx = 1;
				this.dy = 0;
				deadCounter = 0;
				this.setImage(new Image("file:images/lander.png"));
				this.setX(250);
				this.setY(150);
				this.setRotate(0);
				alive = true;
				l.moveRandom();
				l.resetY();
			}
		}
		// System.out.println(dy + ". ." + dx + ". ," + this.getRotate());
		this.getWorld().getObject(ScoreHUD.class).setValues((int) fuel, altitude, VS, HS, score);
	}

	public int getFuel() {
		return (int) fuel;
	}
	
	@Override
	public void move(double dx, double dy) {
		if (this.getX() + this.dx + this.getWidth() > this.getWorld().getWidth() * 0.8) {
			this.getWorld().getObject(Land.class).move(-dx, 0);
		} else if (this.getX() + this.dx < this.getWorld().getWidth() * 0.2) {
			this.getWorld().getObject(Land.class).move(-dx, 0);
		} else {
			super.move(dx, 0);
		}

		if (this.getY() + this.dy + this.getHeight() > this.getWorld().getHeight() * 0.8) {
			this.getWorld().getObject(Land.class).move(0, -dy);
		} else if (this.getY() + this.dy < this.getWorld().getHeight() * 0.15) {
			this.getWorld().getObject(Land.class).move(0, -dy);
		} else {
			super.move(0, dy);
		}
	}

	public int getAltitude() {
		return altitude;
	}

	public int getVerticalSpeed() {
		return VS;
	}

	public int getHorizontalSpeed() {
		return HS;
	}
	
	public void removeHitbox() {
		
	}

	public void addHitbox() {
		this.getWorld().getChildren().add(hitBox);
		hitBox.setFill(Color.ANTIQUEWHITE);
		hitBox.setWidth(this.getWidth());
		hitBox.setHeight(this.getHeight() / 2);
		hitBox.setX(this.getX());
		hitBox.setY(this.getY());
		hitBox.setRotate(this.getRotate());
		hitBox.setFill(Color.TRANSPARENT);
	}

}
