package pl.kabacinsp.voting.voters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters")
@CrossOrigin(origins = "http://localhost:4200")
public class VoterController {

  private final VoterService voterService;

  public VoterController(VoterService voterService) {
    this.voterService = voterService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Voter>> getAllVoters() {
    List<Voter> voters = voterService.getAllVoters();
    return new ResponseEntity<>(voters, HttpStatus.OK);
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<Voter> getVoter(@PathVariable("id") Long id) {
    Voter voter = voterService.getVoter(id);
    return new ResponseEntity<>(voter, HttpStatus.OK);
  }

  @PostMapping("/add")
  public ResponseEntity<Voter> addVoter(@RequestBody Voter voter) {
    Voter newVoter = voterService.addVoter(voter);
    return new ResponseEntity<>(newVoter, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<Voter> updateVoter(@RequestBody Voter voter) {
    Voter updatedVoter = voterService.addVoter(voter);
    return new ResponseEntity<>(updatedVoter, HttpStatus.OK);
  }
}
