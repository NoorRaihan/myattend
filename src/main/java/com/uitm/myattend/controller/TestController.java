package com.uitm.myattend.controller;

import com.uitm.myattend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private final TestService test;

    public TestController(TestService test) {
        this.test = test;
    }
    @GetMapping("/test")
    public String test() {
        return "Test/Test";
    }

    @GetMapping("/all")
    public ArrayList browse() {
        return test.getAll();
    }

    @PostMapping("/product")
    public Map insert() {
        return test.insertData();
    }

    @PutMapping("/product")
    public Map update() { return test.updateData(); }

    @DeleteMapping("/product")
    public Map delete() { return test.deleteData(); }
}
