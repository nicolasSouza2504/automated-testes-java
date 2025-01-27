package br.com.tdd;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchesTest {


    @Test
    void testHamcrestMatchers() {

        List<Integer> score = List.of(99, 100, 101, 105);

        assertThat(score, hasSize(4));
        assertThat(score, hasItems(99, 100));
        assertThat(score, everyItem(greaterThan(90)));
        assertThat(score, everyItem(lessThan(200)));

        assertThat(10, is(10));
        assertThat(10, is(not(20)));
        assertThat(10, is(not(equalTo(20))));
        assertThat(10, is(greaterThan(9)));
        assertThat(10, is(lessThan(11)));
        assertThat(10, is(lessThanOrEqualTo(10)));
        assertThat(10, is(greaterThanOrEqualTo(10)));
        assertThat(10, is(notNullValue()));
        assertThat(null, is(nullValue()));

        assertThat("ABC", isA(String.class));
        assertThat("ABC", allOf(startsWith("A"), endsWith("C"), containsString("B")));
        assertThat("", blankString());
        assertThat(null, blankOrNullString());

    }

}
