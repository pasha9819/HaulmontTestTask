package ru.pasha.views;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import ru.pasha.Constants;
import ru.pasha.entity.Recipe;
import ru.pasha.services.RecipeService;
import ru.pasha.views.components.GridPage;
import ru.pasha.views.components.editors.RecipeEditor;

@SpringUI(path = "/recipes")
public class RecipeUI extends GridPage<Recipe> {

    private final RecipeService recipeService;
    private final RecipeEditor recipeEditor;

    private TextField patientFilter, priorityFilter, descriptionFilter;
    private Button filterButton;
    private HorizontalLayout filters;

    public RecipeUI(RecipeService recipeService, RecipeEditor recipeEditor) {
        super();
        this.recipeService = recipeService;
        this.recipeEditor = recipeEditor;

        grid = new Grid<>(Recipe.class);
        patientFilter = new TextField();
        priorityFilter = new TextField();
        descriptionFilter = new TextField();

        filterButton = new Button(Constants.FILTER_BUTTON_RU_TEXT, clickEvent -> filter());
        filters = new HorizontalLayout();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        recipeEditor.setChangeHandler(this::fillGrid);
        disableDeleteAndEditButton();

        mainLayout.addComponent(header);

        patientFilter.setPlaceholder(Constants.PATIENT_RU_TEXT);
        priorityFilter.setPlaceholder(Constants.PRIORITY_RU_TEXT);
        descriptionFilter.setPlaceholder(Constants.DESCRIPTION_RU_TEXT);

        filters.addComponents(patientFilter, priorityFilter, descriptionFilter, filterButton);
        filters.setWidthFull();
        filters.setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);

        mainLayout.addComponent(filters);

        grid.setWidthFull();
        grid.asSingleSelect().addValueChangeListener(this::gridValueChangeHandler);
        grid.setColumns("doctor", "patient", "priority", "creationDate", "validity", "description");

        grid.getColumn("doctor").setCaption(Constants.DOCTOR_RU_TEXT);
        grid.getColumn("patient").setCaption(Constants.PATIENT_RU_TEXT);
        grid.getColumn("priority").setCaption(Constants.PRIORITY_RU_TEXT);
        grid.getColumn("creationDate").setCaption(Constants.CREATION_DATE_RU_TEXT);
        grid.getColumn("validity").setCaption(Constants.VALIDITY_RU_TEXT);
        grid.getColumn("description").setCaption(Constants.DESCRIPTION_RU_TEXT);

        int columnsCount = grid.getColumns().size();
        grid.getColumns().forEach(col -> col.setMaximumWidth((float) getPage().getBrowserWindowWidth() / columnsCount));

        fillGrid();

        mainLayout.addComponent(grid);
        mainLayout.addComponent(buttonBar);

        setContent(mainLayout);
    }

    @Override
    public void addNewButtonClick() {
        addWindow(recipeEditor);
        recipeEditor.edit(new Recipe());
    }

    @Override
    public void deleteButtonClick() {
        recipeService.delete(grid.asSingleSelect().getValue());
        fillGrid();
    }

    @Override
    public void editButtonClick() {
        addWindow(recipeEditor);
        recipeEditor.edit(grid.asSingleSelect().getValue());
    }

    @Override
    public void gridValueChangeHandler(HasValue.ValueChangeEvent<Recipe> valueChangeEvent) {
        enableDeleteAndEditButton();
    }

    @Override
    public void fillGrid() {
        if (recipeEditor.isAttached()) {
            recipeEditor.close();
        }
        grid.deselectAll();
        disableDeleteAndEditButton();
        grid.setItems(recipeService.findAll());
    }

    private void filter() {
        grid.deselectAll();
        disableDeleteAndEditButton();
        grid.setItems(recipeService.filtered(
                patientFilter.getValue(),
                descriptionFilter.getValue(),
                priorityFilter.getValue()));
    }
}
