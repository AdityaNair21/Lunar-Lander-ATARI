import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	HashSet<KeyCode> keysPressed = new HashSet<KeyCode>();

	private AnimationTimer at;
	private ArrayList<Actor> actorList = new ArrayList<Actor>();

	public abstract void act(long now);

	public World() {
		at = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (int i = 0; i < actorList.size(); i++) {
					actorList.get(i).act(now);
				}
			}

		};
	}

	public void addKeyCode(KeyCode k) {
		keysPressed.add(k);
	}

	public void removeKeyCode(KeyCode k) {
		for(KeyCode key : keysPressed) {
			if(key.equals(k)) {
				keysPressed.remove(key);
			}
		}
	}
	
	public boolean isKeyDown(KeyCode k) {
		for(KeyCode key : keysPressed) {
			if(key.equals(k)) {
				return true;
			}
		}
		return false;
	}

	public void add(Actor actor) {
		actorList.add(actor);
		this.getChildren().add(actor);
	}

	public void remove(Actor actor) {
		actorList.remove(actor);
		this.getChildren().remove(actor);
	}
	
	public void removeAll() {
		actorList.removeAll(actorList);
	}
	
	public boolean hasActor(Actor actor) {
		for(Actor a : actorList) {
			if(a.equals(actor)) {
				return true;
			}
		}
		return false;
	}

	public void start() {
		at.start();
	}

	public void stop() {
		at.stop();
	}

	public <A extends Actor> ArrayList<A> getObjects(Class<A> cls) {
		ArrayList<A> returnList = new ArrayList<A>();
		for (int i = 0; i < actorList.size(); i++) {
			if (actorList.get(i).getClass().equals(cls)) {
				returnList.add((A) actorList.get(i));
			}
		}
		return returnList;
	}
	
	public <A extends Actor> A getObject(Class<A> cls) {
		for (int i = 0; i < actorList.size(); i++) {
			if (actorList.get(i).getClass().equals(cls)) {
				return (A) actorList.get(i);
			}
		}
		return null;
	}
	

}
