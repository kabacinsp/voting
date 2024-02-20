package pl.kabacinsp.voting.voters;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VoterController.class)
class VoterControllerTest {

  private static final String VOTERS_PATH = "/voters";

  @Autowired private VoterController voterController;
  @MockBean private VoterService voterService;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void getAllVotersShouldReturn204NoContent() throws Exception {
    when(voterService.getAllVoters()).thenReturn(new ArrayList<>());

    mockMvc.perform(get(String.format("%s/all", VOTERS_PATH))).andExpect(status().isNoContent());
  }

  @Test
  public void getAllVotersShouldReturn200Ok() throws Exception {
    Voter voter = new Voter("Voter 1");
    Voter voter2 = new Voter("Voter 2");
    List<Voter> voters = List.of(voter, voter2);
    when(voterService.getAllVoters()).thenReturn(voters);

    mockMvc.perform(get(String.format("%s/all", VOTERS_PATH))).andExpect(status().isOk());
  }

  @Test
  public void getVoterShouldReturn404NotFound() throws Exception {
    long voterId = 123L;

    mockMvc
        .perform(get(String.format("%s/find/%s", VOTERS_PATH, voterId)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getVoterShouldReturn200Ok() throws Exception {
    long voterId = 1L;
    Voter voter = new Voter("Voter 1");
    when(voterService.findById(voterId)).thenReturn(voter);

    mockMvc
        .perform(
            get(String.format("%s/find/%s", VOTERS_PATH, voterId))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void addVoterShouldReturn201Created() throws Exception {
    Voter voter = new Voter("Voter 1");
    String requestBody = objectMapper.writeValueAsString(voter);

    mockMvc
        .perform(
            post(String.format("%s/add", VOTERS_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  void updateVoterShouldReturn404NotFound() throws Exception {
    long voterId = 123L;
    Voter voter = new Voter("Voter 1");
    voter.setId(voterId);
    String requestBody = objectMapper.writeValueAsString(voter);
    when(voterService.findById(voterId)).thenReturn(voter);

    mockMvc
        .perform(
            post(String.format("%s/update", VOTERS_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void updateVoterShouldReturn200Ok() throws Exception {
    Voter voter = new Voter("Voter 1");
    voter.setId(123L);
    String requestBody = objectMapper.writeValueAsString(voter);

    mockMvc
        .perform(
            post(String.format("%s/update", VOTERS_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isNotFound());
  }
}
