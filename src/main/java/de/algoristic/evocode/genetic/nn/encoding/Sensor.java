package de.algoristic.evocode.genetic.nn.encoding;

import java.util.ArrayList;
import java.util.List;

public class Sensor implements SendingNeuron {

	private String uuid;
	private String signalEmitter;
	private String variableName;
	private String name;
	private String dataType;
	private Resolvable obtainer;

	private List<Connection> receivers;

	private Sensor(int id, String emitter, String variableName, String name, String dataType, Resolvable obtainer) {
		uuid = ("s_" + id);
		this.signalEmitter = emitter;
		this.variableName = variableName;
		this.name = name;
		this.dataType = dataType;
		this.obtainer = obtainer;
		receivers = new ArrayList<>();
	}

	@Override
	public void addConnection(Connection connection) {
		receivers.add(connection);
	}

	@Override
	public List<Connection> getReceivers() {
		return receivers;
	}

	public String getVariable() {
		return variable();
	}

	public String getObtainer() {
		return obtainer();
	}

	public String getVariableDeclaration(String indent) {
		return new StringBuffer()
			.append(indent)
			.append(dataType)
			.append(" ")
			.append(variable())
			.append(" = ")
			.append(obtainer())
			.append(";")
			.toString();
	}

	private String variable() {
		return variableName;
	}

	private String obtainer() {
		if(obtainer != null) return obtainer.resolve();
		else return new StringBuffer()
			.append(signalEmitter)
			.append(".get")
			.append(name.substring(0, 1).toUpperCase())
			.append(name.substring(1))
			.append("()")
			.toString();
	}

	public static class Builder {
		private final String name;
		private String variableName;
		private String signalEmitter = "this";
		private String dataType = "double";
		private Resolvable obtainer = null;

		public Builder(String name) {
			this.name = name;
			this.variableName = name;
		}

		public Sensor build(int id) {
			return new Sensor(id, signalEmitter, variableName, name, dataType, obtainer);
		}

		public Builder withVariableName(String variableName) {
			this.variableName = variableName;
			return this;
		}

		public Builder withSignalEmitter(String emitter) {
			this.signalEmitter = emitter;
			return this;
		}

		public Builder withDataType(String dataType) {
			this.dataType = dataType;
			return this;
		}

		public Builder withObtainer(Resolvable obtainer) {
			this.obtainer = obtainer;
			return this;
		}
	}

	@Override
	public String getInitializer() {
		return new StringBuffer()
			.append("SensoryNeuron ")
			.append(uuid)
			.append(" = new SensoryNeuron()")
			.toString();
	}

	@Override
	public String getUUID() {
		return uuid;
	}
}
