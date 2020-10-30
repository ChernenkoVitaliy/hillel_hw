package com.ra.course.stackoverflow.services.question;

import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;

public interface QuestionService {

    Question addQuestion(Question question);

    boolean deleteQuestion(Member member, Question question);

    Question undeleteQuestion(Member member, Question question);

    Question openQuestion(Member member, Question question);

    Question closeQuestion(Member member, Question question);

}
