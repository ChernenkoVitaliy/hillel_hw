package com.ra.course.stackoverflow.dao;

import java.util.Optional;

public interface GeneralDao<T> {

    Optional<T> getById(long id);

    T save(T t);

    T update(T t);

    boolean delete(T t);

}
