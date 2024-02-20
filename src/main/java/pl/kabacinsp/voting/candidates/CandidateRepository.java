package pl.kabacinsp.voting.candidates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
  Optional<Candidate> findCandidateById(long id);

  @Query(nativeQuery = true, value = "UPDATE candidates c SET c.votes = (SELECT votes FROM candidates WHERE candidates.id = 1 FOR UPDATE) + 1 WHERE id = 1")
  void setVotes(long id);
}
