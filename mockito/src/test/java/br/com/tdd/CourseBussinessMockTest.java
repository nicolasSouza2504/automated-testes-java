package br.com.tdd;

import br.com.tdd.stub.CourseServiceStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseBussinessMockTest {

    CourseService courseMockService;
    CourseBusiness courseBusiness;
    List<String> courses = Arrays.asList("Spring", "Spring Boot", "Spring MVC");

    @BeforeEach
    void setup() {

        courseMockService = mock(CourseService.class);

        when(courseMockService.retrieveCourses("Dummy")).thenReturn(courses);
        when(courseMockService.retrieveCourses(null)).thenReturn(null);

        courseBusiness = new CourseBusiness(courseMockService);

    }

    @Test
    public void testRetrieveCoursesToStudent_WhenFilterSpring_ShouldReturnOnlySpringCourses() {

        // Given

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

        // When
        List<String> filteredCourses = courseBusiness.retrieveCourses(null);

        // Then
        assertEquals(null, filteredCourses);


    }

}
