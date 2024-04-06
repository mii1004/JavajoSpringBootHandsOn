package com.example.memo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/MemoControllerTest.sql")
@Transactional
class MemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllMemosTest() throws Exception {
        mockMvc.perform(get("/memos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].body", is("takoyaki")))
                .andExpect(jsonPath("$[0].user.userName", is("tanaka")))
                .andExpect(jsonPath("$[0].user.emailAddress", is("tanaka@example.com")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].body", is("okonomiyaki")))
                .andExpect(jsonPath("$[1].user.userName", is("sato")))
                .andExpect(jsonPath("$[1].user.emailAddress", is("sato@example.com")));
    }

    @Test
    void createMemoTest() throws Exception {
        var request = """
                {
                  "body": "dasimaki",
                  "userId": 1
                }
                """;
        mockMvc.perform(post("/memos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body", is("dasimaki")))
                .andExpect(jsonPath("$.user.userName", is("tanaka")))
                .andExpect(jsonPath("$.user.emailAddress", is("tanaka@example.com")));
    }
}