package com.ra.course.stackoverflow.dao;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<T> {

    Optional<T> getById(long id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    boolean delete(T t);

}
