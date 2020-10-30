package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Comment;
import com.ra.course.stackoverflow.entity.Member;

import java.util.List;

public interface AnswerDao extends GeneralDao<Answer> {

    List<Answer> getByAuthor(Member author);

    Answer getByComment(Comment comment);
}
