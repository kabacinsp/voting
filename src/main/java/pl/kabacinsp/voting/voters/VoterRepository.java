package pl.kabacinsp.voting.voters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {
  Optional<Voter> findVoterById(long id);

//  UPDATE voters v set v.voted = TRUE WHERE t.id = ?1 AND t.voted = false;")
//  SELECT CAST(CASE WHEN voted = TRUE AND id = ?1 THEN 1 ELSE 0 END AS bit) as Saleable, * FROM Product
// update voters v set v.is_voted = TRUE where exists (select * from voters v1 WHERE v1.id = 1)

  // update voters v set v.is_voted = TRUE where v.id = 1 AND v.is_voted=FALSE

  /*WITH voters AS (SELECT * FROM voters WHERE id = 1 AND is_voted = false)  UPDATE voters SET is_voted = TRUE;
select * , (select * from voters t2 where t2.id = 1) from voters t;
  SELECT
    FirstName, LastName,
    Salary, DOB,
    CASE Gender
        WHEN 'M' THEN 'Male'
        WHEN 'F' THEN 'Female'
    END
FROM Employees*/
  @Query(
      nativeQuery = true,
      value =
          "SELECT CAST(CASE WHEN voted = TRUE AND id = ?1 THEN 1 ELSE 0 END) FROM voters")
  boolean setVoted(long id);
}
