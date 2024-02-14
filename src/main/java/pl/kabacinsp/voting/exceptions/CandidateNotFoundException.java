package pl.kabacinsp.voting.exceptions;

public class CandidateNotFoundException extends RuntimeException {
  public CandidateNotFoundException(String exceptionMessage) {
    super(exceptionMessage);
  }
}
