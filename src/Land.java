import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Land extends Actor{
ArrayList<Line> landingLocations;	
ArrayList<Line> lines;
//private int generation = 20;
private final int NUM_LINES = 20;
private int Ytranslations;
private double startX = 0.0;
private final double mountain1X = 50.0;
private final double mountain1Y = 200.0;
private double prevX, prevY, temp;
	public Land() {
		//this.setImage(new Image("file:images/grass.png"));
		landingLocations = new ArrayList<Line>();
		lines = new ArrayList<Line>();
		prevX = 50;
		temp = prevX;
		prevY = 795.0;
		Ytranslations = 0;
		//this.generation = generation;
	}
	
	public void initializeLines() {
		//prevX = -100;
		//System.out.println("Start X: " + prevX);
		//Landing location 1
		Line landing1 = new Line(0.0, 795.0, 50.0, 795.0);
		landing1.setStroke(Color.ANTIQUEWHITE);
		landing1.setStrokeWidth(landing1.getStrokeWidth());
		//System.out.println(landing1.getStrokeWidth());
		this.getWorld().getChildren().add(landing1);
		//prevX += 50;
		
		
		for (int i = 1; i <= 10; i++) {
			//Random mountain
			while(prevX <= (temp + mountain1X*2)) {
				addCustomLine(10.0, 5.0);
			}
			
			//Landing location 2
			addLanding(55.0);

			while (prevX <= (temp + mountain1X*2)) {
				addCustomLine(5.0, 10.0);
			}
			
			//Landing location 3
			addLanding(55.0);
			
			temp = prevX;
			while (prevX <= (temp + mountain1X)) {
				addCustomLine(7.5, 7.5);
			}
			
			//Landing location 4
			addLanding(55.0);
			
			temp = prevX;
			while(prevX <= (temp + mountain1X)) {
				addCustomLine(7.5, 25.0);
			}
			
			temp = prevX;
			while (prevX <= temp + mountain1X) {
				addCustomLine(5.0, -35.0);
			}
			//Landing location 5 
			addLanding(55.0);
			
			temp = prevX;
			while (prevX <= temp + mountain1X*2) {
				addCustomLine(5.0, -10.0);
			}
			
			temp = prevX;
			while (prevX <= temp + mountain1X*2) {
				addCustomLine(6.5, 12.5);
			}
			
			//Landing location 6
			addLanding(20.0);
			
			temp = prevX;
			while (prevX <= temp + mountain1X*2) {
				addCustomLine(5.5, 14.0);
			}
			//In between
			temp = prevX;
			while (prevX <= temp + mountain1X*3) {
				addCustomLine(4, -31.0);
			}
			temp = prevX;
			prevY = 795.0;
		}
	}
	
	public void addCustomLine(double customX, double customY) {
		double currentX = Math.random() * (mountain1X/NUM_LINES) + customX;
		double currentY = Math.random() * (mountain1Y/NUM_LINES) + customY;
		Line l = new Line(prevX, prevY, prevX + currentX, prevY - currentY);
		l.setStroke(Color.ANTIQUEWHITE);
		this.getWorld().getChildren().add(l);
		prevX += currentX;
		prevY -= currentY;
		lines.add(l);
	}
	
	public void addLanding(double distance) {
		Line landing = new Line(prevX, prevY, prevX + distance, prevY);
		landing.setStroke(Color.ANTIQUEWHITE);
		this.getWorld().getChildren().add(landing);
		prevX += distance;
		landingLocations.add(landing);
		lines.add(landing);
	}
	
	public boolean isInLanding(Rectangle r) {
		for (int i = 0; i < landingLocations.size(); i++) {
			Line temp = landingLocations.get(i);
			if (r.getBoundsInParent().getMinX() >= temp.getStartX() && (r.getBoundsInParent().getMaxX() <= temp.getEndX() && ((Math.abs((r.getBoundsInParent().getMaxY() - temp.getEndY())) <= 2)))) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public boolean touchingLand(Rectangle r) {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).getBoundsInLocal().intersects(r.getBoundsInLocal())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isIntersectingLand(Rectangle r) {
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			 
			for(int j = 0; j < 1000; j++) {
				double w = l.getEndX() - l.getStartX();
				
				if(r.getBoundsInLocal().contains(l.getStartX() + ((l.getEndX() - l.getStartX())/1000)*j, l.getStartY() + ((Math.abs(l.getEndY() - l.getStartY()))/1000) * j )) {
					return true;
				}
			}
		}
		return false;
		
		
	}
	
	@Override
	public void move(double dx, double dy) {
		Ytranslations += dy;
		for(Line l : lines) {
			l.setEndX(l.getEndX() + dx);
			l.setStartX(l.getStartX() + dx);
			l.setEndY(l.getEndY() + dy);
			l.setStartY(l.getStartY() + dy);
			
		}
	}
	
	public void moveRandom() {
		this.move(-Math.random()*2000, 0);
	}
	
	public void resetY() {
		this.move(0, -Ytranslations);
	}
	
	public int getLowestY() {
		int lowest = (int) lines.get(0).getStartY();
		for(Line l : lines) {
			if(l.getStartY() > lowest) {
				lowest = (int) l.getStartY();
			}
			if(l.getEndY() > lowest) {
				lowest = (int) l.getEndY();
			}
		}
		return lowest;
	}
	
	
	
	@Override
	void act(long now) {

	}
	
	
	
	

}