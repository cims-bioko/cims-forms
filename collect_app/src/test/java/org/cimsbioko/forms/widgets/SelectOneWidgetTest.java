package org.cimsbioko.forms.widgets;

import androidx.annotation.NonNull;

import org.cimsbioko.forms.widgets.base.GeneralSelectOneWidgetTest;

/**
 * @author James Knight
 */

public class SelectOneWidgetTest extends GeneralSelectOneWidgetTest<AbstractSelectOneWidget> {

    @NonNull
    @Override
    public SelectOneWidget createWidget() {
        return new SelectOneWidget(activity, formEntryPrompt, false);
    }
}
