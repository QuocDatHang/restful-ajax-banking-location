package com.cg.service;

import java.util.List;

public interface IGeneralService<E, T> {
    List<E> findAll();
    E findById(T id);
    void save(E e);
    void delete(T id);
}
