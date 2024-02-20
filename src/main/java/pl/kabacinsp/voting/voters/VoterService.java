package pl.kabacinsp.voting.voters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kabacinsp.voting.exceptions.VoterNotFoundException;

import java.util.List;

@Service
public class VoterService {
  private final VoterRepository voterRepository;

  @Autowired
  public VoterService(VoterRepository voterRepository) {
    this.voterRepository = voterRepository;
  }

  public Voter addVoter(Voter voter) {
    return voterRepository.save(voter);
  }

  public Voter findById(long id) {
    return voterRepository
        .findVoterById(id)
        .orElseThrow(() -> new VoterNotFoundException("Couldn't find voter: " + id));
  }

  public List<Voter> getAllVoters() {
    return voterRepository.findAll();
  }

  public boolean setVoted(long id) {
    return voterRepository.setVoted(id);
  }
}
