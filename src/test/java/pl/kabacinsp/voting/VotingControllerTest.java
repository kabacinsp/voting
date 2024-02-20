package pl.kabacinsp.voting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.kabacinsp.voting.candidates.Candidate;
import pl.kabacinsp.voting.candidates.CandidateService;
import pl.kabacinsp.voting.voters.Voter;
import pl.kabacinsp.voting.voters.VoterService;

import java.sql.Connection;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotingController.class)
class VotingControllerTest {

  private static final String VOTE_PATH = "/voting";
  @MockBean private VoterService voterService;
  @MockBean private CandidateService candidateService;
  @MockBean private ConnectionFactory connectionFactory;
  @MockBean private Connection connection;
  @MockBean private Statement statement;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void voteShouldReturn200Ok() throws Exception {
    Voter voter = new Voter("Voter 1");
    voter.setId(1L);

    Candidate candidate = new Candidate("Candidate 1");
    candidate.setId(1L);

    when(voterService.findById(anyLong())).thenReturn(voter);
    when(candidateService.findById(anyLong())).thenReturn(candidate);
    when(connectionFactory.createConnection()).thenReturn(connection);
    when(connection.createStatement()).thenReturn(statement);

    mockMvc
        .perform(
            get(String.format("%s/vote", VOTE_PATH))
                .param("voter", "1")
                .param("candidate", "1"))
        .andExpect(status().isOk());
  }

  @Test
  public void voteShouldReturn404NotFound() throws Exception {
    mockMvc
        .perform(
            get(String.format("%s/vote", VOTE_PATH)).param("voter", "1").param("candidate", "1"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void voteShouldReturn406NotAcceptable() throws Exception {
    Voter voter = new Voter("Voter 1");
    voter.setId(1L);
    voter.setVoted(true);

    Candidate candidate = new Candidate("Candidate 1");
    candidate.setId(1L);

    when(voterService.findById(anyLong())).thenReturn(voter);
    when(candidateService.findById(anyLong())).thenReturn(candidate);

    mockMvc
        .perform(
            get(String.format("%s/vote", VOTE_PATH))
                .param("voter", voter.getId().toString())
                .param("candidate", candidate.getId().toString()))
        .andExpect(status().isNotAcceptable());
  }
}
