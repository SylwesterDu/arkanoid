package pl.kielce.tu.arkanoid;

import javafx.scene.paint.Color;

public class Block {
	final double x1;
	final double y1;
	final double x2;
	final double y2;
	final double width;
	final double height;
	final Color color;
	private boolean exists = true;
	public Block(double x1, double y1, double width, double height, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.width = width;
		this.height = height;
		this.color = color;
		x2 = this.x1 + width;
		y2 = this.x2 + height;
	}
	
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
	public boolean getExists() {
		return exists;
	}

}
