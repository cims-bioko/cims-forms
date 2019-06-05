package org.cimsbioko.forms.widgets.base;

import org.javarosa.core.model.data.IAnswerData;
import org.cimsbioko.forms.widgets.ExStringWidget;

/**
 * @author James Knight
 */

public abstract class GeneralExStringWidgetTest<W extends ExStringWidget, A extends IAnswerData> extends BinaryWidgetTest<W, A> {

    @Override
    public Object createBinaryData(A answerData) {
        return answerData.getDisplayText();
    }
}
