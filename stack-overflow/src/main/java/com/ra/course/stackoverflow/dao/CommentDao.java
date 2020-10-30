package com.ra.course.stackoverflow.dao;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Comment;

public interface CommentDao extends GeneralDao<Comment> {

    Comment getByAnswer(Answer answer);
}
