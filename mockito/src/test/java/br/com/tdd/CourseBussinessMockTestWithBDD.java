package br.com.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.*;

public class CourseBussinessMockTestWithBDD {

    CourseService courseMockService;
    CourseBusiness courseBusiness;
    List<String> courses = Arrays.asList("Spring", "Spring Boot", "Spring MVC");

    @BeforeEach
    void setup() {

        courseMockService = mock(CourseService.class);

        given(courseMockService.retrieveCourses("Dummy")).willReturn(courses);
        given(courseMockService.retrieveCourses(null)).willReturn(null);

        courseBusiness = new CourseBusiness(courseMockService);

    }

    @Test
    public void testRetrieveCoursesToStudent_WhenFilterSpring_ShouldReturnOnlySpringCourses() {

        // Given

        // When
        List<String> filteredCourses = courseBusiness.retrieveCourses("Dummy");

        // Then
        assertThat(filteredCourses.size(), is(3));
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
