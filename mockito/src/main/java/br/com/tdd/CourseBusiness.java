package br.com.tdd;

import java.util.ArrayList;
import java.util.List;

// SUT - System (Method) Under Test
public class CourseBusiness {

    private CourseService courseService;

    public CourseBusiness(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<String> retrieveCourses(String student) {

        if (student == null || student.isBlank()) {
            return null;
        }

        List<String> courses = courseService.retrieveCourses(student);
        List<String> filteredCourses = new ArrayList<>();

        for (String course : courses) {
            if (course.contains("Spring")) {
                filteredCourses.add(course);
            }
        }

        return filteredCourses;

    }

    public void deleteCoursesNotRelatedToSpring(String student) {

            List<String> courses = courseService.retrieveCourses(student);

            for (String course : courses) {
                if (!course.contains("Spring")) {
                    courseService.deleteCourse(course);
                }
            }

    }
}
