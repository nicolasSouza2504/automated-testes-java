package br.com.tdd;

import java.util.List;

public interface CourseService {
    public List<String> retrieveCourses(String student);

    void deleteCourse(String course);
}
