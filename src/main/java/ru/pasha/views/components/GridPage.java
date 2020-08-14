package ru.pasha.views.components;

import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import ru.pasha.Constants;

public abstract class GridPage<T> extends UI {
    protected VerticalLayout mainLayout;

    protected Header header;

    protected Button addNewButton, editButton, deleteButton;

    protected HorizontalLayout buttonBar;

    protected Grid<T> grid;

    public GridPage() {
        super();
        mainLayout = new VerticalLayout();

        header = new Header();

        addNewButton = new Button(Constants.ADD_RU_TEXT, clickEvent -> {
            addNewButtonClick();
        });

        deleteButton = new Button(Constants.DELETE_RU_TEXT, clickEvent -> {
            deleteButtonClick();
        });

        editButton = new Button(Constants.EDIT_RU_TEXT, clickEvent -> {
            editButtonClick();
        });

        buttonBar = new HorizontalLayout(addNewButton, editButton, deleteButton);
    }

    protected void enableDeleteAndEditButton() {
        deleteButton.setEnabled(true);
        editButton.setEnabled(true);
    }

    protected void disableDeleteAndEditButton() {
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
    }

    public abstract void addNewButtonClick();

    public abstract void deleteButtonClick();

    public abstract void editButtonClick();

    public abstract void gridValueChangeHandler(HasValue.ValueChangeEvent<T> valueChangeEvent);

    public abstract void fillGrid();
}
