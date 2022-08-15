package com.example.utspt2072028graceag.dao;

import javafx.collections.ObservableList;

import java.util.List;

public interface OldDaoInterface<T> {
    ObservableList<T> getData();
    int addData(T data);
    int delData(T data);

    int updateData(T data);
}
