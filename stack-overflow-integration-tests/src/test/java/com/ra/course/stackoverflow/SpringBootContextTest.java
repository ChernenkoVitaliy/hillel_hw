package com.ra.course.stackoverflow;

import com.ra.course.stackoverflow.services.answer.AnswerServiceImpl;
import com.ra.course.stackoverflow.services.comment.CommentServiceImpl;
import com.ra.course.stackoverflow.services.question.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
