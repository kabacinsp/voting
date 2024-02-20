package pl.kabacinsp.voting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kabacinsp.voting.candidates.Candidate;
import pl.kabacinsp.voting.candidates.CandidateService;
import pl.kabacinsp.voting.voters.Voter;
import pl.kabacinsp.voting.voters.VoterService;

import java.sql.*;
import java.util.Arrays;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/voting")
@CrossOrigin(origins = "http://localhost:4200")
public class VotingController {

  @Autowired private CandidateService candidateService;
  @Autowired private VoterService voterService;
  @Autowired private ConnectionFactory connectionFactory;

  @GetMapping("/vote")
  public ResponseEntity<Object> vote(
      @RequestParam("voter") Long voter, @RequestParam("candidate") Long candidate) {
    Voter whoVotes = voterService.findById(voter);
    Candidate voteFor = candidateService.findById(candidate);
    if (whoVotes == null || voteFor == null) {
      return ResponseEntity.status(NOT_FOUND).build();
    }

    if (whoVotes.isVoted()) {
      return ResponseEntity.status(NOT_ACCEPTABLE).build();
    }

    return voteStatement();
  }

  private ResponseEntity<Object> voteStatement() {
    try (Connection conn = connectionFactory.createConnection();
        Statement statement = conn.createStatement()) {
      statement.addBatch(
          "UPDATE voters v SET v.is_voted = TRUE WHERE v.id = 1 AND v.is_voted=FALSE");
      statement.addBatch(
          "UPDATE candidates c SET c.votes = (SELECT votes FROM candidates WHERE candidates.id = 1 FOR UPDATE) + 1 WHERE id = 1");
      statement.executeBatch();
    } catch (SQLException e) {
      return ResponseEntity.internalServerError().build();
    }
    return ResponseEntity.ok().build();
  }
}
