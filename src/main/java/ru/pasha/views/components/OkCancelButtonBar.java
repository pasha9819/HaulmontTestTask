package ru.pasha.views.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import ru.pasha.Constants;

public class OkCancelButtonBar extends HorizontalLayout {
    private final Button ok;
    private final Button cancel;

    public OkCancelButtonBar() {
        ok = new Button(Constants.OK_BUTTON_RU_TEXT);
        cancel = new Button(Constants.CANCEL_BUTTON_RU_TEXT);
        addComponents(ok, cancel);
    }

    public Button getOk() {
        return ok;
    }

    public Button getCancel() {
        return cancel;
    }
}
