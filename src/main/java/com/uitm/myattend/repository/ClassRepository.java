package com.uitm.myattend.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ClassRepository {

    private final DBRepository commDB;

    public ClassRepository(DBRepository commDB) {
        this.commDB = commDB;
    }


}
