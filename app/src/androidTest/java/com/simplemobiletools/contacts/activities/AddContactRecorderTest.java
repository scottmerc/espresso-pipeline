package com.simplemobiletools.contacts.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddContactRecorderTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void addContactTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction myFloatingActionButton = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.fragment_fab_button),
                        childAtPosition(
                                allOf(withId(com.simplemobiletools.contacts.R.id.contacts_fragment),
                                        childAtPosition(
                                                withId(com.simplemobiletools.contacts.R.id.viewpager),
                                                0)),
                                1),
                        isDisplayed()));
        myFloatingActionButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(com.simplemobiletools.contacts.R.id.contact_first_name)).perform(typeText("Testname"));

        ViewInteraction myEditText = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.contact_first_name),
                        childAtPosition(
                                allOf(withId(com.simplemobiletools.contacts.R.id.contact_holder),
                                        childAtPosition(
                                                withId(com.simplemobiletools.contacts.R.id.contact_scrollview),
                                                0)),
                                5)));
        myEditText.perform(scrollTo(), replaceText("TestName"), closeSoftKeyboard());

        ViewInteraction myEditText2 = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.contact_surname),
                        childAtPosition(
                                allOf(withId(com.simplemobiletools.contacts.R.id.contact_holder),
                                        childAtPosition(
                                                withId(com.simplemobiletools.contacts.R.id.contact_scrollview),
                                                0)),
                                7)));
        myEditText2.perform(scrollTo(), replaceText("TestSurname"), closeSoftKeyboard());

        ViewInteraction myEditText3 = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.contact_number),
                        childAtPosition(
                                allOf(withId(com.simplemobiletools.contacts.R.id.contact_number_holder),
                                        childAtPosition(
                                                withId(com.simplemobiletools.contacts.R.id.contact_numbers_holder),
                                                0)),
                                0)));
        myEditText3.perform(scrollTo(), replaceText("8888885522"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.save), withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.simplemobiletools.contacts.R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(com.simplemobiletools.contacts.R.id.contact_name), withText("TestName TestSurname"),
                        childAtPosition(
                                allOf(withId(com.simplemobiletools.contacts.R.id.contact_holder),
                                        childAtPosition(
                                                withId(com.simplemobiletools.contacts.R.id.contact_frame),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("TestName TestSurname")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
