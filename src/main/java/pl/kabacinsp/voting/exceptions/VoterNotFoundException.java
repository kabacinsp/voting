package pl.kabacinsp.voting.exceptions;

public class VoterNotFoundException extends RuntimeException {
  public VoterNotFoundException(String exceptionMessage) {
    super(exceptionMessage);
  }
}
