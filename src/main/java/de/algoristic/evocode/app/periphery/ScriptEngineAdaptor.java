package de.algoristic.evocode.app.periphery;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public interface ScriptEngineAdaptor {
	double evaluate(String expression);

	public static ScriptEngineAdaptor getInstance() {
		return new InternalScriptEngine();
	}

	class InternalScriptEngine implements ScriptEngineAdaptor {

		static ScriptEngine internalScriptEngineSingleton;

		static {
			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			internalScriptEngineSingleton = scriptEngineManager.getEngineByName("JavaScript");

		}

		@Override
		public double evaluate(String expression) {
			Object result;
			try {
				result = internalScriptEngineSingleton.eval(expression);
			} catch (ScriptException e) {
				throw new RuntimeException("Fitness function could not be evaluated", e);
			}
			double evaluated = 0;
			if (result instanceof Double) {
				evaluated = (double) result;
			} else if (result instanceof Integer) {
				evaluated = (double) (int) result;
			}
			return evaluated;
		}
	}
}