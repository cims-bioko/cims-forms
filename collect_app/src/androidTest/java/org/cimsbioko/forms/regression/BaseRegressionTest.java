package org.cimsbioko.forms.regression;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.cimsbioko.forms.activities.MainMenuActivity;

public class BaseRegressionTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> main = new ActivityTestRule<>(MainMenuActivity.class);
}