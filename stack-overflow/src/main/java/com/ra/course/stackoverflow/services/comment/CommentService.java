package com.ra.course.stackoverflow.services.comment;

import com.ra.course.stackoverflow.entity.Answer;
import com.ra.course.stackoverflow.entity.Comment;
import com.ra.course.stackoverflow.entity.Member;

public interface CommentService {

    Comment addComment(Answer answer, Comment comment);

    boolean deleteComment(Member member, Comment comment);

}
