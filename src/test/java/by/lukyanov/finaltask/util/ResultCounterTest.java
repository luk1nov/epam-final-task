package by.lukyanov.finaltask.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultCounterTest {
    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private static final int POSTS_PER_PAGE = 15;
    private static final int POSTS = 50;

    @Test
    void offsetForFirstPage() {
        int expectedOffset = 0;
        ResultCounter counter = new ResultCounter(FIRST_PAGE, POSTS_PER_PAGE);
        assertEquals(expectedOffset, counter.offset());
    }

    @Test
    void offsetForNotFirstPage() {
        int expectedOffset = 15;
        ResultCounter counter = new ResultCounter(SECOND_PAGE, POSTS_PER_PAGE);
        assertEquals(expectedOffset, counter.offset());
    }

    @Test
    void countPagesTest() {
        int expectedPages = 4;
        assertEquals(expectedPages, ResultCounter.countPages(POSTS, POSTS_PER_PAGE));
    }
}