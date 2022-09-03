import java.util.ArrayList;
import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView{
	
	public Actor() {
		
	}
	
	abstract void act(long now);
	
	void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
	}
	
	World getWorld() {
		try {
			return (World)(getParent());
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public double getWidth() {
		return getBoundsInLocal().getWidth();
	}
	
	public double getHeight() {
		return getBoundsInLocal().getHeight();
	}
	
	public <A extends Actor> ArrayList<A> getIntersectingObjects(java.lang.Class<A> cls){
		ArrayList<A> actors = this.getWorld().getObjects(cls);
		ArrayList<A> returnList = new ArrayList<A>();
		
		for (int i = 0; i < actors.size(); i++) {
			if (!this.equals(actors.get(i)) && actors.get(i).getClass().equals(cls) && this.intersects(actors.get(i).getBoundsInLocal())) {
					returnList.add(actors.get(i));
			}
		}
		return returnList;
	}
	
	public <A extends Actor> A getOneIntersectingObject(Class<A> cls) {
		ArrayList<A> actors = this.getWorld().getObjects(cls);
		
		for (int i = 0; i < actors.size(); i++) {
			if (!this.equals(actors.get(i)) && actors.get(i).getClass().equals(cls) && this.intersects(actors.get(i).getBoundsInLocal())) {
					return (actors.get(i));
			}
		}
		return null;
	}
	
	public void moveDown(double amount) {
		this.move(0, amount);
	}
	
	public void moveUp(double amount) {
		this.move(0, -1 * amount);
	}
	
	public void moveLeft(double amount) {
		this.move((-1 * amount), 0);
	}
	
	public void moveRight(double amount) {
		this.move(amount, 0);
	}
	
	
	//Finds distance to another actor
	public int distanceTo(Actor other) {
		if(this.getOneIntersectingObject(other.getClass()).equals(other)) {
			return 0;
		} else {
			int dist = 0;
			while(dist < 1000 || this.getOneIntersectingObject(other.getClass()) == null) {
				
			}
			
			
		}
		
		return 0;
	}
	
	
	
}
