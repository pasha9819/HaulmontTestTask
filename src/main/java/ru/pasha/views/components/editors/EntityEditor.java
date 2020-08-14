package ru.pasha.views.components.editors;

import com.vaadin.data.Binder;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ru.pasha.views.components.OkCancelButtonBar;


public abstract class EntityEditor<T> extends Window {
    ChangeHandler changeHandler;
    T instance;
    Binder<T> binder;
    OkCancelButtonBar bar;
    VerticalLayout mainLayout;

    EntityEditor() {
        super();
        bar = new OkCancelButtonBar();

        bar.getOk().addClickListener(clickEvent -> save());
        bar.getCancel().addClickListener(clickEvent -> close());

        mainLayout = new VerticalLayout();
        mainLayout.setWidth("350px");

        center();
        setModal(true);
    }

    void setComponentsFullWidth() {
        for (int i = 0; i < mainLayout.getComponentCount(); i++) {
            mainLayout.getComponent(i).setWidthFull();
        }
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    public abstract void save();

    public abstract void edit(T object);
}
