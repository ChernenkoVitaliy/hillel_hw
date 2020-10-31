package com.ra.course.stackoverflow;

import com.ra.course.stackoverflow.dao.AnswerDao;
import com.ra.course.stackoverflow.dao.CommentDao;
import com.ra.course.stackoverflow.dao.QuestionDao;
import com.ra.course.stackoverflow.services.answer.AnswerServiceImpl;
import com.ra.course.stackoverflow.services.comment.CommentServiceImpl;
import com.ra.course.stackoverflow.services.question.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class SpringBootContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void checkContextForAvailabilityOfQuestionService() {
        assertNotNull(applicationContext.getBean(QuestionServiceImpl.class));
    }

    @Test
    public void checkContextForAvailabilityOfAnswerService() {
        assertNotNull(applicationContext.getBean(AnswerServiceImpl.class));
    }

    @Test
    public void checkContextForAvailabilityOfCommentService() {
        assertNotNull(applicationContext.getBean(CommentServiceImpl.class));
    }

    @TestConfiguration
    static class SpringBootContextTestConfiguration {

        @Primary
        @Bean
        public QuestionDao mockedQuestionDao() {
            return mock(QuestionDao.class);
        }

        @Primary
        @Bean
        public AnswerDao mockedAnswerDao() {
            return mock(AnswerDao.class);
        }

        @Primary
        @Bean
        public CommentDao mockedCommentDao() {
            return mock(CommentDao.class);
        }
    }
}
