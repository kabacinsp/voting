package pl.kabacinsp.voting;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kabacinsp.voting.candidates.Candidate;
import pl.kabacinsp.voting.candidates.CandidateService;
import pl.kabacinsp.voting.voters.Voter;
import pl.kabacinsp.voting.voters.VoterService;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestController
@RequestMapping("/voting")
@CrossOrigin(origins = "http://localhost:4200")
public class VotingRestController {

  private final CandidateService candidateService;
  private final VoterService voterService;

  public VotingRestController(CandidateService candidateService, VoterService voterService) {
    this.candidateService = candidateService;
    this.voterService = voterService;
  }

  @GetMapping("/vote")
  public ResponseEntity<Object> vote(
      @RequestParam("voter") Long voter, @RequestParam("candidate") Long candidate) {
    Voter whoVotes = voterService.getVoter(voter);
    Candidate voteFor = candidateService.getCandidate(candidate);
    if (whoVotes.isVoted()) return ResponseEntity.status(NOT_ACCEPTABLE).build();

    whoVotes.setVoted(true);
    voteFor.setVotes(voteFor.getVotes() + 1);
    voterService.addVoter(whoVotes);
    candidateService.addCandidate(voteFor);
    return ResponseEntity.ok().build();
  }
}
