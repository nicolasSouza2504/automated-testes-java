package br.com.tdd;

import br.com.tdd.stub.CourseServiceStub;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseBussinesStubTest {

    @Test
    public void testRetrieveCoursesToStudent_WhenFilterSpring_ShouldReturnOnlySpringCourses() {

        // Given
        CourseService courseService = new CourseServiceStub();
        CourseBusiness courseBusiness = new CourseBusiness(courseService);

        // When
        List<String> filteredCourses = courseBusiness.retrieveCourses("Dummy");

        // Then
        assertEquals(3, filteredCourses.size());
        assertTrue(filteredCourses.contains("Spring"));
        assertTrue(filteredCourses.contains("Spring Boot"));

    }


    @Test
    public void testRetrieveCourses_WhenNullStudent_ShouldReturnNull() {

        // Given
        CourseService courseService = new CourseServiceStub();
        CourseBusiness courseBusiness = new CourseBusiness(courseService);

        // When
        List<String> filteredCourses = courseBusiness.retrieveCourses(null);

        // Then
        assertEquals(null, filteredCourses);


    }
}
