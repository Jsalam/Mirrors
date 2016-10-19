import processing.core.*;

public class Logic  {
	
	PApplet app;
	Room room;
	
	public Logic(PApplet app){
		this.app = app;
		room = new Room(app);
		room.populate(8,2);// mirrors / people
	}
	
	public void show(){
		room.show();
		if(app.keyPressed){
			room.addVisitor();
		}
	}

	
}
