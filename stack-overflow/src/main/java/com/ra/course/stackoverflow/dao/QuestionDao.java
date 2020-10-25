package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Member;
import com.ra.course.stackoverflow.entity.Question;
import com.ra.course.stackoverflow.entity.Tag;

import java.util.List;

public interface QuestionDao extends GeneralDao<Question> {

    List<Question> getByAuthor(Member author);

    List<Question> getByTag(Tag tag);

    List<Question> getByTitle(String searchPhrase);
}
