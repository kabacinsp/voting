package pl.kabacinsp.voting.candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatesController {

  @Autowired private CandidateService candidateService;

  @GetMapping("/all")
  public ResponseEntity<List<Candidate>> getAllCandidates() {
    try {
      List<Candidate> candidates = candidateService.getAllCandidates();

      if (candidates.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(candidates, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<Candidate> getCandidate(@PathVariable("id") Long id) {
    Candidate candidate = candidateService.findById(id);
    if (candidate != null) {
      return new ResponseEntity<>(candidate, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
    try {
      Candidate newCandidate = candidateService.addCandidate(new Candidate(candidate.getName()));
      return new ResponseEntity<>(newCandidate, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/update")
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
    Candidate existingCandidate = candidateService.findById(candidate.getId());

    if (existingCandidate != null) {
      existingCandidate.setVotes(existingCandidate.getVotes() + 1);
      return new ResponseEntity<>(existingCandidate, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
