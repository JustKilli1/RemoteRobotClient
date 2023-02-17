package remoterobot;

import shared.Utils;

public class MeasureData {
	private Ground ground;
	private double temperature;
	public MeasureData(Ground ground, double temperature) {
		this.ground = ground;
		this.temperature = temperature;
	}
	
	public static MeasureData fromString(String str) {
		String[] results = str.split("\\|");
		Ground groundResults = Ground.valueOf(results[1]);
		int temperatureResults = Utils.toInt(results[2]).get();
		return new MeasureData (groundResults, temperatureResults);
		//[0] SIZE
		//[1] width
		//[2] height
	}
	public String toString() {
		return "MeasureData|" + ground+ "|" + temperature+ "|";
	}
	public Ground getGround() {
		return ground;
	}
	public double getTemperature() {
		return temperature;
	}
}

