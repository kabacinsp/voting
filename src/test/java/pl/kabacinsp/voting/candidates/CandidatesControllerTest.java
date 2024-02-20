package pl.kabacinsp.voting.candidates;

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

@WebMvcTest(CandidatesController.class)
class CandidatesControllerTest {
  private static final String CANDIDATES_PATH = "/candidates";

  @Autowired private CandidatesController candidatesController;
  @MockBean private CandidateService candidateService;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  public void getAllCandidatesShouldReturn204NoContent() throws Exception {
    when(candidateService.getAllCandidates()).thenReturn(new ArrayList<>());

    mockMvc
        .perform(get(String.format("%s/all", CANDIDATES_PATH)))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getAllCandidatesShouldReturn200Ok() throws Exception {
    Candidate candidate = new Candidate("Candidate 1");
    Candidate candidate2 = new Candidate("Candidate 2");
    List<Candidate> candidates = List.of(candidate, candidate2);
    when(candidateService.getAllCandidates()).thenReturn(candidates);

    mockMvc.perform(get(String.format("%s/all", CANDIDATES_PATH))).andExpect(status().isOk());
  }

  @Test
  public void getCandidateShouldReturn404NotFound() throws Exception {
    long candidateId = 123L;

    mockMvc
        .perform(get(String.format("%s/find/%s", CANDIDATES_PATH, candidateId)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getCandidateShouldReturn200Ok() throws Exception {
    long candidateId = 1L;
    Candidate candidate = new Candidate("Candidate 1");
    when(candidateService.findById(candidateId)).thenReturn(candidate);

    mockMvc
        .perform(
            get(String.format("%s/find/%s", CANDIDATES_PATH, candidateId))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void addCandidateShouldReturn201Created() throws Exception {
    Candidate candidate = new Candidate("Candidate 1");
    String requestBody = objectMapper.writeValueAsString(candidate);

    mockMvc
        .perform(
            post(String.format("%s/add", CANDIDATES_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  void updateCandidateShouldReturn404NotFound() throws Exception {
    long candidateId = 123L;
    Candidate candidate = new Candidate("Candidate 1");
    candidate.setId(candidateId);
    String requestBody = objectMapper.writeValueAsString(candidate);
    when(candidateService.findById(candidateId)).thenReturn(candidate);

    mockMvc
        .perform(
            post(String.format("%s/update", CANDIDATES_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void updateCandidateShouldReturn200Ok() throws Exception {
    Candidate candidate = new Candidate("Candidate 1");
    candidate.setId(123L);
    String requestBody = objectMapper.writeValueAsString(candidate);

    mockMvc
        .perform(
            post(String.format("%s/update", CANDIDATES_PATH))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .content(requestBody))
        .andExpect(status().isNotFound());
  }
}
