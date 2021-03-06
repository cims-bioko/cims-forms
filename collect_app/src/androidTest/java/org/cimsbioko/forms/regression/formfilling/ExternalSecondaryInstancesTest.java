package org.cimsbioko.forms.regression.formfilling;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.cimsbioko.forms.espressoutils.FormEntry;
import org.cimsbioko.forms.espressoutils.MainMenu;
import org.cimsbioko.forms.regression.BaseRegressionTest;
import org.cimsbioko.forms.support.CopyFormRule;
import org.cimsbioko.forms.support.ResetStateRule;

import java.util.Collections;

// Issue number NODK-377
@RunWith(AndroidJUnit4.class)
public class ExternalSecondaryInstancesTest extends BaseRegressionTest {

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(new ResetStateRule())
            .around(new CopyFormRule("internal_select_10.xml"))
            .around(new CopyFormRule("external_select_10.xml", Collections.singletonList("external_data_10.xml")));

    @Test
    public void external_ShouldFillTheForm() {

        //TestCase1
        MainMenu.startBlankForm("external select 10");
        FormEntry.clickOnText("b");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickOnText("ba");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickSaveAndExit();
    }

    @Test
    public void internal_ShouldFillTheForm() {

        //TestCase2
        MainMenu.startBlankForm("internal select 10");
        FormEntry.clickOnText("c");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickOnText("ca");
        FormEntry.swipeToNextQuestion();
        FormEntry.clickSaveAndExit();
    }
}