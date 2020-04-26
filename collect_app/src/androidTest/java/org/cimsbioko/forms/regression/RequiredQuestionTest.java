package org.cimsbioko.forms.regression;

import android.Manifest;

import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.cimsbioko.forms.espressoutils.FormEntry;
import org.cimsbioko.forms.espressoutils.MainMenu;
import org.cimsbioko.forms.support.CopyFormRule;
import org.cimsbioko.forms.support.ResetStateRule;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.pressBack;


// Issue number NODK-249
@RunWith(AndroidJUnit4.class)
public class RequiredQuestionTest extends BaseRegressionTest {

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(Manifest.permission.READ_PHONE_STATE))
            .around(new ResetStateRule())
            .around(new CopyFormRule("requiredJR275.xml"));

    @Test
    public void requiredQuestions_ShouldDisplayAsterisk() {

        //TestCase1
        MainMenu.startBlankForm("required");
        FormEntry.checkIsTextDisplayed("* Foo");
        closeSoftKeyboard();
        pressBack();
        FormEntry.clickIgnoreChanges();
    }

    @Test
    public void requiredQuestions_ShouldDisplayCustomMessage() {

        //TestCase2
        MainMenu.startBlankForm("required");
        FormEntry.swipeToNextQuestion();
        FormEntry.checkIsToastWithMessageDisplayes("Custom required message", main);
        closeSoftKeyboard();
        pressBack();
        FormEntry.clickIgnoreChanges();
    }
}