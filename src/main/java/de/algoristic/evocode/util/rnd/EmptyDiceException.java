package de.algoristic.evocode.util.rnd;

public class EmptyDiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public EmptyDiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
