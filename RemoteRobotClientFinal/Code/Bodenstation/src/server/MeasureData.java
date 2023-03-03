package server;

import java.io.Serializable;
import java.util.Optional;

public class MeasureData implements Serializable {
	private static final long serialVersionUID = 2L;
	private Ground ground;
	private float temperature;
	private int robotId;

	// Konstante fuer unbekannte Temperatur
	public static final float TEMP_UNKNOWN = -999.9f;

	public MeasureData(Ground ground, float temperature) {
		setMeasure(ground, temperature);
	}
	public MeasureData(Ground ground, float temperature, int robotId) {
		setMeasure(ground, temperature);
		this.robotId = robotId;
	}

	public MeasureData(Ground ground) {
		this(ground, TEMP_UNKNOWN);
	}

	public MeasureData() {
		this(Ground.NICHTS, TEMP_UNKNOWN);
	}

	public Ground getGround() {
		return ground;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setMeasure(Ground ground, float temperature){
		this.ground = ground;
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MEASURE|");
		sb.append(ground.name());
		sb.append("|");
		sb.append(temperature);
		return sb.toString();
	}

	public static Optional<MeasureData> parse(String s) {
		String[] split = s.split(":");
		String[] token = split[1].trim().split("\\|");
		if (token.length == 3) {
			if (token[0].equals("MEASURE")) {
				try {
					Ground g = Ground.valueOf(token[1]);
					float temp = Float.parseFloat(token[2]);
					return Optional.of(new MeasureData(g, temp, Integer.parseInt(split[0])));
				} catch (Exception e) {
				}
			}
		}
		return Optional.empty();
	}

	public int getRobotId() {
		return robotId;
	}
}

