package org.cimsbioko.forms.widgets;

import androidx.annotation.NonNull;

import org.cimsbioko.forms.widgets.base.GeneralSelectOneWidgetTest;

/**
 * @author James Knight
 */
public class SelectOneSearchWidgetTest extends GeneralSelectOneWidgetTest<SelectOneSearchWidget> {

    @NonNull
    @Override
    public SelectOneSearchWidget createWidget() {
        return new SelectOneSearchWidget(activity, formEntryPrompt, false);
    }
}
