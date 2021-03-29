package pl.kielce.tu.arkanoid;

public class Ball {
	double xPos;
	double yPos;
	final double diameter;
	final double radius;
	double xMovement;
	double yMovement;
	final double ballSpeed;
	public Ball(double xPos, double yPos, double diameter, double xMovement, double yMovement) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.diameter = diameter;
		this.radius = diameter/2;
		this.xMovement = xMovement;
		this.yMovement = yMovement;
		ballSpeed = Math.sqrt(Math.pow(xMovement, 2) + Math.pow(yMovement, 2));
	}

	
}
