package com.secmngsys.global.route.test;

import java.util.Collection;

/**
 * Service interface for managing todos.
 */
public interface TodoService {
    Collection<Todo> findNotCompleted();

    Collection<Todo> findCompleted();

    long deleteCompleted();

    Todo findById(long id);

    void create(Todo todo);

    Todo update(Todo todo, long id);

    void deleteOne(long id);

    Collection<Todo> listAll();
}