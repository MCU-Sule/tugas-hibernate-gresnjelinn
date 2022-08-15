package com.example.utspt2072028graceag.dao;

import com.example.utspt2072028graceag.model.MovieEntity;
import com.example.utspt2072028graceag.model.UserEntity;
import com.example.utspt2072028graceag.util.HiberUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao implements DaoInterface<UserEntity> {

    @Override
    public List<UserEntity> getData() {
        List<UserEntity> uList;
        SessionFactory sf = new HiberUtil().getSessionFactory();
        Session s = sf.openSession();

        CriteriaBuilder bob = s.getCriteriaBuilder();
        CriteriaQuery q = bob.createQuery(UserEntity.class);
        Root<MovieEntity> r = q.from(UserEntity.class);

        uList = s.createQuery(q).getResultList();

        s.close();
        return uList;
    }

    @Override
    public void addData(UserEntity data) {
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
    public void delData(UserEntity data) {
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
    public void updateData(UserEntity data) {
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
}
