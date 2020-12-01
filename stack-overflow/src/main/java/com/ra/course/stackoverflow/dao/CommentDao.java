package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Comment;

import java.util.List;

public interface CommentDao extends GeneralDao<Comment> {

    List<Comment> getByAnswer(Answer answer);
}
