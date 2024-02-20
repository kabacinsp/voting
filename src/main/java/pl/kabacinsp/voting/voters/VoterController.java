package pl.kabacinsp.voting.voters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voters")
@CrossOrigin(origins = "http://localhost:4200")
public class VoterController {

  @Autowired private VoterService voterService;

  @GetMapping("/all")
  public ResponseEntity<List<Voter>> getAllVoters(
      @RequestParam(required = false) boolean canVoted) {
    try {
      List<Voter> voters;

      if (canVoted)
        voters =
            voterService.getAllVoters().stream()
                .filter(voter -> !voter.isVoted())
                .collect(Collectors.toList());
      else voters = voterService.getAllVoters();

      if (voters.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(voters, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<Voter> getVoter(@PathVariable("id") Long id) {
    Voter voter = voterService.findById(id);
    if (voter != null) {
      return new ResponseEntity<>(voter, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Voter> addVoter(@RequestBody Voter voter) {
    try {
      Voter newVoter = voterService.addVoter(new Voter(voter.getName()));
      return new ResponseEntity<>(newVoter, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/update")
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public ResponseEntity<Voter> updateVoter(@RequestBody Voter voter) {
    Voter existingVoter = voterService.findById(voter.getId());

    if (existingVoter != null) {
      existingVoter.setVoted(voter.isVoted());
      return new ResponseEntity<>( voterService.addVoter(existingVoter), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
