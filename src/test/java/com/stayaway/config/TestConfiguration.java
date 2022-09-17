package com.stayaway.config;


import com.stayaway.TestBoardFactory;
import com.stayaway.TestMvc;
import com.stayaway.service.BoardFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

@Configuration
public class TestConfiguration {

    @Bean
    public TestMvc getTestMvc(MockMvc mockMvc) {
        return new TestMvc(mockMvc);
    }

    @Bean
    @Primary
    public BoardFactory getBoardFactory() {
        return new TestBoardFactory();
    }
}
