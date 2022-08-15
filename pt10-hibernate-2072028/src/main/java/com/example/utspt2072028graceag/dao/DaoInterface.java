package com.example.utspt2072028graceag.dao;

import javafx.collections.ObservableList;

import java.util.List;

public interface DaoInterface<T> {
    List<T> getData();
    void addData(T data);
    void delData(T data);

    void updateData(T data);
}
