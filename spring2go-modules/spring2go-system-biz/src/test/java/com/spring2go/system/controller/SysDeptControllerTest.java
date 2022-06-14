package com.spring2go.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring2go.common.core.domain.R;
import com.spring2go.system.entity.SysDept;
import com.spring2go.system.service.SysDeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SysDeptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SysDeptService sysDeptService;

    @Test
    void getById() throws Exception {
        // given
        SysDept dept = new SysDept();
        dept.setDeptId(1L);
        dept.setDeptName("测试部门");
        given(sysDeptService.getById(1L))
                .willReturn(dept);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/sys/dept/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        ObjectMapper objectMapper = new ObjectMapper();
        assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
        assertThat(response.getContentAsString(StandardCharsets.UTF_8), is(equalTo(objectMapper.writeValueAsString(R.ok(dept)))));
    }
}