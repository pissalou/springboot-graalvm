package com.pma.springbootgraalvm;

import com.pma.springbootgraalvm.advice.GlobalLayoutAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void springBootDiscoversOneGlobalLayoutAdviceBean() {
        assertThat(applicationContext.getBeansOfType(GlobalLayoutAdvice.class))
            .hasSize(1);
    }
}

