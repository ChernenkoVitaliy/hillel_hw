package com.ra.course.stackoverflow.services.answer;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;

public interface AnswerService {

    Answer addAnswer(Answer answer, Question question);

    boolean deleteAnswer(Member member, Answer answer);

}
