package com.uitm.myattend.service;

import com.uitm.myattend.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class TestService {

    @Autowired
    private final TestRepository test;

    public TestService(TestRepository test) {
        this.test = test;
    }

    public ArrayList getAll() {
        return test.getData();
    }

    public HashMap insertData() {
        return test.insertData();
    }

    public HashMap updateData() { return test.updateData(); }

    public HashMap deleteData() { return test.deleteData(); }
}
