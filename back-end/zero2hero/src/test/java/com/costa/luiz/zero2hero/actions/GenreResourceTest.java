package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreResource.class)
class GenreResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GenreRepository genreRepository;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        doReturn(Collections.singletonList(Genre.builder().id(42L).name("Comedy").build()))
                .when(genreRepository).findAll();
        this.mockMvc.perform(get("/api/v1/genres")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(42L))
                .andExpect(jsonPath("$[0].name").value("Comedy"));
    }
}