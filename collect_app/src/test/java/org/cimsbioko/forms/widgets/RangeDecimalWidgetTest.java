package org.cimsbioko.forms.widgets;

import androidx.annotation.NonNull;

import org.javarosa.core.model.data.DecimalData;
import org.cimsbioko.forms.widgets.base.RangeWidgetTest;

/**
 * @author James Knight
 */

public class RangeDecimalWidgetTest extends RangeWidgetTest<RangeDecimalWidget, DecimalData> {

    @NonNull
    @Override
    public RangeDecimalWidget createWidget() {
        return new RangeDecimalWidget(activity, formEntryPrompt);
    }

    @NonNull
    @Override
    public DecimalData getNextAnswer() {
        return new DecimalData(random.nextDouble());
    }
}
