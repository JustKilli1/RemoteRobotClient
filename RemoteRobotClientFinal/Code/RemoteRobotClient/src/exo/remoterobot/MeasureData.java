package exo.remoterobot;

import exo.planet.Ground;

import java.io.Serializable;
import java.util.Optional;

public class MeasureData implements Serializable {
	private static final long serialVersionUID = 2L;
	protected Ground ground;
	protected float temperature;
	// Konstante fuer unbekannte Temperatur
	public static final float TEMP_UNKNOWN = -999.9f;

	public MeasureData(Ground ground, float temperature) {
		setMeasure(ground, temperature);
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
		String[] token = s.trim().split("\\|");
		if (token.length == 3) {
			if (token[0].equals("MEASURE")) {
				try {
					Ground g = Ground.valueOf(token[1]);
					float temp = Float.parseFloat(token[2]);
					return Optional.of(new MeasureData(g, temp));
				} catch (Exception e) {
				}
			}
		}
		return Optional.empty();
	}
}

