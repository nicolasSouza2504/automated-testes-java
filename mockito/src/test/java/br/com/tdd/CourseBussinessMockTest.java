package br.com.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class CourseBussinessMockTest {

    CourseService courseMockService;
    CourseBusiness courseBusiness;
    List<String> courses = Arrays.asList("Spring", "Spring Boot", "Spring MVC", "Quarkus", "Node");

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

    @Test
    @DisplayName("Test delete courses not related to spring using mock should call method")
    public void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallDeleteMethod() {

        // Given

        // When
        courseBusiness.deleteCoursesNotRelatedToSpring("Dummy");

        // Then
        verify(courseMockService).deleteCourse("Quarkus");
        verify(courseMockService).deleteCourse("Node");
        verify(courseMockService, never()).deleteCourse("Spring");

        verify(courseMockService, atLeast(2)).deleteCourse(anyString());

    }

    @Test
    @DisplayName("Test delete courses not related to spring using argument capturing should call method")
    public void testDeleteCoursesNotRelatedToSpring_UsingArgumentCapturing_ShouldCallDeleteMethod() {

        // Given
        when(courseMockService.retrieveCourses("Dummy")).thenReturn(courses);


        // When
        courseBusiness.deleteCoursesNotRelatedToSpring("Dummy");

        // Then

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(courseMockService, times(2)).deleteCourse(argumentCaptor.capture());

        assertThat( argumentCaptor.getAllValues().size(), is(2));

    }

}
