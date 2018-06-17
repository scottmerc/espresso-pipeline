package com.simplemobiletools.contacts.activities;

/**
 * Created by maksim on 6/11/18.
 */

import android.Manifest;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.simplemobiletools.contacts.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddContactTest {


    // Launch Activity under test
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    // Grant App permissions
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CONTACTS)
                                                                           .grant(Manifest.permission.WRITE_CONTACTS);

    @Test
    public void addContactTest() throws InterruptedException {

        // Test Data

        String testName = "TestName";
        String testSurname = "TestSurname";
        String testPhoneNumber = "8887776655";
        String testContactName = "TestName TestSurname";

        // Element Locators

        Matcher addButtonLocator = allOf(withContentDescription("add_button"), isDisplayed());
        Matcher firstNameTextFieldLocator = withId(R.id.contact_first_name);
        Matcher surnameTextFieldLocator = withId(R.id.contact_surname);
        Matcher phoneNumberLocator = withId(R.id.contact_number);
        Matcher saveButtonLocator = withId(R.id.save);
        Matcher contactInListLocator = withId(R.id.contact_name);

        // Execute test
        onView(addButtonLocator).perform(click());
        onView(firstNameTextFieldLocator).perform(typeText(testName));
        onView(surnameTextFieldLocator).perform(typeText(testSurname));
        onView(phoneNumberLocator).perform(typeText(testPhoneNumber));
        onView(saveButtonLocator).perform(click());

        // Add sleep statement
        Thread.sleep(500);

        // Verify expected behavior
        onView(contactInListLocator).check(matches(withText(testContactName)));

    }

}
