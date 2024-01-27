package com.uitm.myattend.repository;

import org.springframework.stereotype.Repository;

@Repository
public class AttendanceRepository {

    private final DBRepository commDB;

    public AttendanceRepository(DBRepository commDB) {
        this.commDB = commDB;
    }
}
