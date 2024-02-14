package pl.kabacinsp.voting.candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kabacinsp.voting.exceptions.CandidateNotFoundException;

import java.util.List;

@Service
public class CandidateService {

  private final CandidateRepository candidateRepository;

  @Autowired
  public CandidateService(CandidateRepository candidateRepository) {
    this.candidateRepository = candidateRepository;
  }

  public Candidate addCandidate(Candidate candidate) {
    return candidateRepository.save(candidate);
  }

  public Candidate getCandidate(long id) {
    return candidateRepository
        .findCandidateById(id)
        .orElseThrow(() -> new CandidateNotFoundException("Couldn't find candidate: " + id));
  }

  public List<Candidate> getAllCandidates() {
    return candidateRepository.findAll();
  }
}
