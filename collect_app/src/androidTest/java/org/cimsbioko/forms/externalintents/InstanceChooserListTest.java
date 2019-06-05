package org.cimsbioko.forms.externalintents;

import androidx.test.filters.Suppress;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.cimsbioko.forms.activities.InstanceChooserList;

import java.io.IOException;

import static org.cimsbioko.forms.externalintents.ExportedActivitiesUtils.testDirectories;

@Suppress
// Frequent failures: https://github.com/opendatakit/collect/issues/796
@RunWith(AndroidJUnit4.class)
public class InstanceChooserListTest {

    @Rule
    public ActivityTestRule<InstanceChooserList> instanceChooserListRule =
            new ExportedActivityTestRule<>(InstanceChooserList.class);

    @Test
    public void instanceChooserListMakesDirsTest() throws IOException {
        testDirectories();
    }

}
