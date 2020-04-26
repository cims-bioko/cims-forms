/*
 * Copyright 2019 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cimsbioko.forms.formentry;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.cimsbioko.forms.R;
import org.cimsbioko.forms.activities.FormEntryActivity;
import org.cimsbioko.forms.support.CopyFormRule;
import org.cimsbioko.forms.support.ResetStateRule;
import org.cimsbioko.forms.test.FormLoadingUtils;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Tests that intent groups work as documented at https://docs.opendatakit.org/launch-apps-from-collect/#launching-external-apps-to-populate-multiple-fields
 */
public class IntentGroupTest {
    private static final String INTENT_GROUP_FORM = "intent-group.xml";

    @Rule
    public ActivityTestRule<FormEntryActivity> activityTestRule = FormLoadingUtils.getFormActivityTestRuleFor(INTENT_GROUP_FORM);

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(new ResetStateRule())
            .around(new CopyFormRule(INTENT_GROUP_FORM));

    // Verifies that a value given to the label text with form buttonText is used as the button text.
    @Test
    public void buttonName_ShouldComeFromSpecialFormText() {
        onView(withText(R.string.launch_app)).check(doesNotExist());
        onView(withText("This is buttonText")).check(matches(isDisplayed()));
    }

    // Verifies that a value given to the label text with form noAppErrorString is used as the toast
    // text if no app is found.
    @Test
    public void appMissingErrorText_ShouldComeFromSpecialFormText() {
        onView(withText("This is buttonText")).perform(click());
        onView(withText("This is noAppErrorString")).inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void externalApp_ShouldPopulateFields() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("sometext", "Here is a text value");

        int randomInteger = (new Random()).nextInt(255);
        resultIntent.putExtra("someinteger", randomInteger);

        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultIntent));
        onView(withText("This is buttonText")).perform(click());

        onView(withText("Here is a text value")).check(matches(isDisplayed()));
        onView(withText(Integer.toString(randomInteger))).check(matches(isDisplayed()));
    }
}
