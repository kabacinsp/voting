package pl.kabacinsp.voting.candidates;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
  Optional<Candidate> findCandidateById(long id);
}
