package pl.kabacinsp.voting.voters;

import jakarta.persistence.*;

@Entity
@Table(name = "voters")
public class Voter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private boolean isVoted;

  public Voter() { }

  public Voter(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isVoted() {
    return isVoted;
  }

  public void setVoted(boolean voted) {
    isVoted = voted;
  }
}
