package org.cimsbioko.forms.widgets;

import androidx.annotation.NonNull;

import org.cimsbioko.forms.widgets.base.GeneralSelectMultiWidgetTest;

/**
 * @author James Knight
 */

public class ListMultiWidgetTest extends GeneralSelectMultiWidgetTest<ListMultiWidget> {
    @NonNull
    @Override
    public ListMultiWidget createWidget() {
        return new ListMultiWidget(activity, formEntryPrompt, true);
    }
}
