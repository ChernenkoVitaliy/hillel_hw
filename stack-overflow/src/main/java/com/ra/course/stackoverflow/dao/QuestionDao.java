package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;

import java.util.List;

public interface QuestionDao extends GeneralDao<Question> {

    List<Question> getByAuthor(Member author);

    List<Question> getByTitle(String searchPhrase);

    Question getByAnswer(Answer answer);
}
