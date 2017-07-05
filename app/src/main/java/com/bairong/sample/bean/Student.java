package com.bairong.sample.bean;

import java.util.List;

/**
 * Created by zhangwei on 17/3/27.
 */

public class Student {
    String name;
    String stu_no;
    List<String> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStu_no() {
        return stu_no;
    }

    public void setStu_no(String stu_no) {
        this.stu_no = stu_no;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
