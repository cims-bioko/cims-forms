package org.cimsbioko.forms.widgets;

import androidx.annotation.NonNull;

import org.cimsbioko.forms.widgets.base.GeneralSelectMultiWidgetTest;

/**
 * @author James Knight
 */

public class SelectMultiWidgetTest extends GeneralSelectMultiWidgetTest<SelectMultiWidget> {
    @NonNull
    @Override
    public SelectMultiWidget createWidget() {
        return new SelectMultiWidget(activity, formEntryPrompt);
    }
}
