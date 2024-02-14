package pl.kabacinsp.voting.voters;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
  Optional<Voter> findVoterById(long id);
}
