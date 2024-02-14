package pl.kabacinsp.voting.candidates;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "http://localhost:4200")
public class CandidatesRestController {

  private final CandidateService candidateService;

  public CandidatesRestController(CandidateService candidateService) {
    this.candidateService = candidateService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Candidate>> getAllCandidates() {
    List<Candidate> candidates = candidateService.getAllCandidates();
    return new ResponseEntity<>(candidates, HttpStatus.OK);
  }

  @GetMapping("/find/{id}")
  public ResponseEntity<Candidate> getCandidate(@PathVariable("id") Long id) {
    Candidate candidate = candidateService.getCandidate(id);
    return new ResponseEntity<>(candidate, HttpStatus.OK);
  }

  @PostMapping("/add")
  public ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
    Candidate newCandidate = candidateService.addCandidate(candidate);
    return new ResponseEntity<>(newCandidate, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
    Candidate updatedCandidate = candidateService.addCandidate(candidate);
    return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
  }
}
