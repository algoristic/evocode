package de.algoristic.evocode.genetic.nn.encoding;

public class SensorDivider implements Resolvable {

	private Sensor sensor;
	private String divisor;

	public SensorDivider(Sensor sensor, String divisor) {
		this.sensor = sensor;
		this.divisor = divisor;
	}

	@Override
	public String resolve() {
		return new StringBuffer()
			.append("(")
			.append(sensor.getObtainer())
			.append(" / ")
			.append(divisor)
			.append(")")
			.toString();
	}
}
