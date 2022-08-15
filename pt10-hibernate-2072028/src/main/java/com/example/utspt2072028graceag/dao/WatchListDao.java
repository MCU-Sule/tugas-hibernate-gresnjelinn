package com.example.utspt2072028graceag.dao;

import com.example.utspt2072028graceag.model.Movie;
import com.example.utspt2072028graceag.model.User;
import com.example.utspt2072028graceag.model.WatchList;
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

public class WatchListDao implements DaoInterface<WatchListEntity> {

    @Override
    public List<WatchListEntity> getData() {
        List<WatchListEntity> wlList;
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();

        CriteriaBuilder bob = s.getCriteriaBuilder();
        CriteriaQuery q = bob.createQuery(WatchListEntity.class);
        q.from(WatchListEntity.class);

        wlList = s.createQuery(q).getResultList();

        s.close();
        return wlList;
    }

    @Override
    public void addData(WatchListEntity data) {
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
    public void delData(WatchListEntity data) {
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
    public void updateData(WatchListEntity data) {
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

    public List<WatchListEntity> getFilteredData(int id) {
        List<WatchListEntity> wlList;
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();

        CriteriaBuilder bob = s.getCriteriaBuilder();
        CriteriaQuery q = bob.createQuery(WatchListEntity.class);
        Root<WatchListEntity> r = q.from(WatchListEntity.class);

        Predicate p1 = bob.equal(r.get("idUser"), id);
        q.where(p1);

        wlList = s.createQuery(q).getResultList();

        s.close();
        return wlList;
    }
}
