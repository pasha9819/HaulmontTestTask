package ru.pasha.views;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasha.Constants;
import ru.pasha.entity.Patient;
import ru.pasha.services.PatientService;
import ru.pasha.views.components.GridPage;
import ru.pasha.views.components.editors.PatientEditor;

@SpringUI(path = "/patients")
public class PatientsUI extends GridPage<Patient> {

    private final PatientEditor patientEditor;
    private final PatientService patientService;

    @Autowired
    public PatientsUI(PatientEditor patientEditor, PatientService patientService) {
        super();
        this.patientEditor = patientEditor;
        this.patientService = patientService;

        grid = new Grid<>(Patient.class);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        patientEditor.setChangeHandler(this::fillGrid);
        disableDeleteAndEditButton();

        mainLayout.addComponent(header);

        grid.setWidthFull();

        grid.asSingleSelect().addValueChangeListener(this::gridValueChangeHandler);
        grid.setColumns("surname", "name", "patronymic", "number");
        grid.getColumn("surname").setCaption(Constants.SURNAME_RU_TEXT);
        grid.getColumn("name").setCaption(Constants.NAME_RU_TEXT);
        grid.getColumn("patronymic").setCaption(Constants.PATRONYMIC_RU_TEXT);
        grid.getColumn("number").setCaption(Constants.NUMBER_RU_TEXT);

        int columnsCount = grid.getColumns().size();
        grid.getColumns().forEach(col -> col.setMaximumWidth((float) getPage().getBrowserWindowWidth() / columnsCount));

        fillGrid();

        mainLayout.addComponent(grid);
        mainLayout.addComponent(buttonBar);

        setContent(mainLayout);
    }

    @Override
    public void addNewButtonClick() {
        addWindow(patientEditor);
        patientEditor.edit(new Patient());
    }

    @Override
    public void deleteButtonClick() {
        try{
            patientService.delete(grid.asSingleSelect().getValue());
            fillGrid();
        }catch (IllegalStateException e){
            Notification.show(Constants.PATIENT_HAS_RECIPES_ERROR);
        }
    }

    @Override
    public void editButtonClick() {
        addWindow(patientEditor);
        patientEditor.edit(grid.asSingleSelect().getValue());
    }

    @Override
    public void gridValueChangeHandler(HasValue.ValueChangeEvent<Patient> valueChangeEvent) {
        enableDeleteAndEditButton();
    }

    @Override
    public void fillGrid() {
        if (patientEditor.isAttached()) {
            patientEditor.close();
        }
        grid.deselectAll();
        disableDeleteAndEditButton();
        grid.setItems(patientService.findAll());
    }
}
