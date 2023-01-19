package com.secmngsys.global.route.test;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private Map<Long, Todo> todos = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(1);

    @Override
    public Collection<Todo> findNotCompleted() {
        return todos.values().stream().filter(todo -> !todo.isCompleted()).collect(Collectors.toList());
    }

    @Override
    public Collection<Todo> findCompleted() {
        return todos.values().stream().filter(Todo::isCompleted).collect(Collectors.toList());
    }

    @Override
    public long deleteCompleted() {
        long completed = todos.values().stream().filter(Todo::isCompleted).count();

        todos.entrySet().removeIf(entry -> entry.getValue().isCompleted());

        return completed;
    }

    @Override
    public Todo findById(long id) {
        return todos.get(id);
    }

    @Override
    public void create(Todo todo) {
        todo.setId(counter.getAndIncrement());
        todos.put(todo.getId(), todo);
    }

    @Override
    public Todo update(Todo todo, long id) {
        Todo persistedTodo = todos.get(id);
        if (persistedTodo != null) {
            persistedTodo.setCompleted(todo.isCompleted());
            persistedTodo.setTitle(todo.getTitle());
            persistedTodo.setOrder(todo.getOrder());
            persistedTodo.setUrl(todo.getUrl());

            return persistedTodo;
        }
        return null;
    }

    @Override
    public void deleteOne(long id) {
        todos.remove(id);
    }

    @Override
    public Collection<Todo> listAll() {
        return todos.values();
    }
}