package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.os.Bundle;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class SudokuActivityTest  {

    @Rule
    public ActivityTestRule<SudokuActivity> mActivityTestRule =
            new ActivityTestRule<>(SudokuActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testStateString() {
        onView(withId(R.id.language_switch)).check(matches(withId(R.id.language_switch)));
    }
}