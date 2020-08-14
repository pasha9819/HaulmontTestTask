package ru.pasha.views;

import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import ru.pasha.Constants;
import ru.pasha.entity.Doctor;
import ru.pasha.services.DoctorService;
import ru.pasha.views.components.GridPage;
import ru.pasha.views.components.editors.DoctorEditor;

@SpringUI(path = "/doctors")
public class DoctorsUI extends GridPage<Doctor> {

    private final DoctorEditor doctorEditor;
    private final DoctorService doctorService;

    private Button statisticButton;
    private boolean statisticIsOpen;

    public DoctorsUI(DoctorEditor doctorEditor, DoctorService doctorService) {
        super();
        this.doctorEditor = doctorEditor;
        this.doctorService = doctorService;

        grid = new Grid<>(Doctor.class);

        statisticButton = new Button(Constants.OPEN_STATISTIC_RU_TEXT, clickEvent -> this.statisticButtonClick());
    }

    private void statisticButtonClick() {
        statisticIsOpen = !statisticIsOpen;

        statisticButton.setCaption(statisticIsOpen ? Constants.CLOSE_STATISTIC_RU_TEXT : Constants.OPEN_STATISTIC_RU_TEXT);

        if (statisticIsOpen){
            grid.addColumn(Doctor::normalCount).setId("normal").setCaption("NORMAL");
            grid.addColumn(Doctor::citoCount).setId("cito").setCaption("CITO");
            grid.addColumn(Doctor::statimCount).setId("statim").setCaption("STATIM");
        }else {
            grid.removeColumn("normal");
            grid.removeColumn("cito");
            grid.removeColumn("statim");
        }

        setColumnsMaxWidth();
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        doctorEditor.setChangeHandler(this::fillGrid);
        disableDeleteAndEditButton();

        mainLayout.addComponent(header);
        mainLayout.addComponent(statisticButton);

        grid.setWidthFull();

        grid.asSingleSelect().addValueChangeListener(this::gridValueChangeHandler);

        grid.setColumns("surname", "name", "patronymic", "specialization");
        grid.getColumn("surname").setCaption(Constants.SURNAME_RU_TEXT);
        grid.getColumn("name").setCaption(Constants.NAME_RU_TEXT);
        grid.getColumn("patronymic").setCaption(Constants.PATRONYMIC_RU_TEXT);
        grid.getColumn("specialization").setCaption(Constants.SPECIALIZATION_RU_TEXT);

        setColumnsMaxWidth();

        fillGrid();

        mainLayout.addComponent(grid);
        mainLayout.addComponent(buttonBar);

        setContent(mainLayout);
    }

    private void setColumnsMaxWidth() {
        int columnsCount = grid.getColumns().size();
        grid.getColumns().forEach(col -> col.setMaximumWidth((float) getPage().getBrowserWindowWidth() / columnsCount));
    }

    @Override
    public void addNewButtonClick() {
        addWindow(doctorEditor);
        doctorEditor.edit(new Doctor());
    }

    @Override
    public void deleteButtonClick() {
        try{
            doctorService.delete(grid.asSingleSelect().getValue());
            fillGrid();
        }catch (IllegalStateException e){
            Notification.show(Constants.DOCTOR_HAS_RECIPES_ERROR);
        }
    }

    @Override
    public void editButtonClick() {
        addWindow(doctorEditor);
        doctorEditor.edit(grid.asSingleSelect().getValue());
    }

    @Override
    public void gridValueChangeHandler(HasValue.ValueChangeEvent<Doctor> valueChangeEvent) {
        enableDeleteAndEditButton();
    }

    @Override
    public void fillGrid() {
        if (doctorEditor.isAttached()) {
            doctorEditor.close();
        }
        grid.deselectAll();
        disableDeleteAndEditButton();
        grid.setItems(doctorService.findAll());
    }
}
