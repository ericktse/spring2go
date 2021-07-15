package com.spring2go.common.core.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class StringUtilsTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("--------------- Before All -------------");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("--------------- After All -------------");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("--------------- Before Each -------------");
    }

    @AfterEach
    void afterEach() {
        System.out.println("--------------- After Each -------------");
    }


    @Test
    void isEmpty() {
        String emptyStr = "";
        assertTrue(StringUtils.isEmpty(emptyStr));
    }

    @Test
    void isNotEmpty() {
        String emptyStr = "not empty";

        //使用第三方Assertions库hamcrest
        //@link:https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions-third-party
        assertThat(StringUtils.isNotEmpty(emptyStr), is(equalTo(true)));
    }
}