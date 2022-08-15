package com.example.utspt2072028graceag.dao;

import com.example.utspt2072028graceag.model.Movie;
import com.example.utspt2072028graceag.model.MovieEntity;
import com.example.utspt2072028graceag.model.WatchListEntity;
import com.example.utspt2072028graceag.util.HiberUtil;
import com.example.utspt2072028graceag.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDao implements DaoInterface<MovieEntity> {
    @Override
    public List<MovieEntity> getData() {
        List<MovieEntity> mList;
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();

        CriteriaBuilder bob = s.getCriteriaBuilder();
        CriteriaQuery q = bob.createQuery(MovieEntity.class);
        Root<MovieEntity> r = q.from(MovieEntity.class);

        mList = s.createQuery(q).getResultList();

        s.close();
        return mList;
    }

    @Override
    public void addData(MovieEntity data) {
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();
        Transaction t = s.beginTransaction();
        try {
            s.save(data);
            t.commit();
        } catch (Exception e) {
            t.rollback();
        }
        s.close();
    }

    @Override
    public void delData(MovieEntity data) {
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();
        Transaction t = s.beginTransaction();
        try {
            s.delete(data);
            t.commit();
        } catch (Exception e) {
            t.rollback();
        }
        s.close();
    }

    @Override
    public void updateData(MovieEntity data) {
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();
        Transaction t = s.beginTransaction();
        try {
            s.update(data);
            t.commit();
        } catch (Exception e) {
            t.rollback();
        }
        s.close();
    }

    public List<MovieEntity> getFilteredData(String genre) {
        List<MovieEntity> mList;
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();

        CriteriaBuilder bob = s.getCriteriaBuilder();
        CriteriaQuery q = bob.createQuery(MovieEntity.class);
        Root<MovieEntity> r = q.from(MovieEntity.class);

        Predicate p1 = bob.like(r.get("genre"), genre);
        q.where(p1);

        mList = s.createQuery(q).getResultList();

        s.close();
        return mList;
    }
}
