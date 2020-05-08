package com.xz.Sorting.entity;

public class Student implements Comparable<Student> {
    private int age;
    private int score;

    public Student(int score, int age) {
        this.score = score;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }
}
