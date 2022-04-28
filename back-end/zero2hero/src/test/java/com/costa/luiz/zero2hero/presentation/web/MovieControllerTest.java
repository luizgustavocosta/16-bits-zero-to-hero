package com.costa.luiz.zero2hero.presentation.web;

import com.costa.luiz.zero2hero.persistence.repository.movie.Classification;
import com.costa.luiz.zero2hero.presentation.web.movie.MovieController;
import com.costa.luiz.zero2hero.business.service.MovieService;
import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.business.service.dto.MovieMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    private final String PATH = "/api/v1/movies";

    private final long ID = 42L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MovieMapper mapper;

    @MockBean
    MovieService service;

    @Test
    @WithMockUser(roles={"OTHERS"})
    void deleteMovie() throws Exception {
        doNothing().when(service).deleteById(ID);
        this.mockMvc.perform(delete(PATH + "/{id}",String.valueOf(ID))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteBlockedForUserUnauthorized() throws Exception {
        this.mockMvc.perform(delete(PATH + "/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        var movieDto = MovieDto.builder()
                .id(ID)
                .name("Os Trapalhões")
                .classification(Classification.G)
                .country("Brazil")
                .duration(120)
                .language("Português")
                .rating(8.2d)
                .year(1995)
                .build();
        var request = new ObjectMapper().writeValueAsString(movieDto);

        this.mockMvc.perform(put(PATH)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}