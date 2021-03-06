package org.cimsbioko.forms.regression;

import android.Manifest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.cimsbioko.forms.R;
import org.cimsbioko.forms.espressoutils.FormEntry;
import org.cimsbioko.forms.espressoutils.MainMenu;
import org.cimsbioko.forms.support.CopyFormRule;
import org.cimsbioko.forms.support.ResetStateRule;

import static androidx.test.espresso.Espresso.pressBack;

// Issue number NODK-219
@RunWith(AndroidJUnit4.class)
public class SpinnerWidgetTest extends BaseRegressionTest {

    @Rule
    public RuleChain copyFormChain = RuleChain
            .outerRule(GrantPermissionRule.grant(Manifest.permission.READ_PHONE_STATE))
            .around(new ResetStateRule())
            .around(new CopyFormRule("selectOneMinimal.xml"));

    @Test
    public void spinnerList_ShouldDisplay() {
        MainMenu.startBlankForm("selectOneMinimal");
        FormEntry.clickOnString(R.string.select_one);
        FormEntry.clickOnAreaWithIndex("CheckedTextView", 2);
        FormEntry.clickOnText("c");
        FormEntry.checkIsTextDisplayed("c");
        FormEntry.checkIfTextDoesNotExist("a");
        FormEntry.checkIfTextDoesNotExist("b");
        pressBack();
        FormEntry.swipeToNextQuestion();
        FormEntry.clickSaveAndExit();
    }
}
