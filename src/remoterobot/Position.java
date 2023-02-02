package remoterobot;

import shared.Utils;

public class Position {
	private int posX, posY;
	private Direction direction;
	public Position(int posX, int posY, Direction direction) {
		this.posX = posX;
		this.posY = posY;
		this.direction = direction;
	}
	
	public static Position fromString(String str) {
		//SIZE|width|height
		String[] results = str.split("\\|");
		//if(results.length != 3) return null;
		int posXResults = Utils.toInt(results[1]).get();
		int posYResults = Utils.toInt(results[2]).get();
		Direction directionResults = Direction.valueOf(results[3]);
		return new Position (posXResults, posYResults, directionResults);
		//[0] SIZE
		//[1] width
		//[2] height
	}
	public String toString() {
		return "Position|" + posX+ "|" + posY+ "|" + direction;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public Direction getDirection() {
		return direction;
	}
	
	
	
}
