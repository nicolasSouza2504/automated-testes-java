package br.com.tdd.stub;

import br.com.tdd.CourseService;

import java.util.Arrays;
import java.util.List;

public class CourseServiceStub implements CourseService {

    @Override
    public List<String> retrieveCourses(String student) {
        return Arrays.asList("Spring", "Spring Boot", "Spring MVC");
    }

}
