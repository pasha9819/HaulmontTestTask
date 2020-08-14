package ru.pasha.views.components.editors;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import ru.pasha.Constants;
import ru.pasha.entity.Doctor;
import ru.pasha.services.DoctorService;

@SpringComponent
@UIScope
public class DoctorEditor extends EntityEditor<Doctor> {
    private final DoctorService doctorService;

    private TextField name, patronymic, surname, specialization;

    public DoctorEditor(DoctorService doctorService) {
        super();
        this.doctorService = doctorService;

        binder = new Binder<>(Doctor.class);

        surname = new TextField(Constants.SURNAME_RU_TEXT, "");
        name = new TextField(Constants.NAME_RU_TEXT, "");
        patronymic = new TextField(Constants.PATRONYMIC_RU_TEXT, "");
        specialization = new TextField(Constants.SPECIALIZATION_RU_TEXT, "");

        binder
                .forField(surname)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .withValidator(Constants.STRING_LENGTH_DEFAULT_VALIDATOR)
                .bind("surname");

        binder
                .forField(name)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .withValidator(Constants.STRING_LENGTH_DEFAULT_VALIDATOR)
                .bind("name");

        binder
                .forField(patronymic)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .withValidator(Constants.STRING_LENGTH_DEFAULT_VALIDATOR)
                .bind("patronymic");

        binder
                .forField(specialization)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .withValidator(Constants.STRING_LENGTH_DEFAULT_VALIDATOR)
                .bind("specialization");

        binder.bindInstanceFields(this);

        mainLayout.addComponents(surname, name, patronymic, specialization);
        setComponentsFullWidth();
        mainLayout.addComponent(bar);

        setContent(mainLayout);
    }

    @Override
    public void save() {
        if (!binder.isValid()) {
            binder.validate();
            Notification.show(Constants.CHECK_FIELD_FILLING_CORRECTION);
            return;
        }
        doctorService.save(instance);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    @Override
    public void edit(Doctor object) {
        if (object == null) {
            return;
        }

        if (object.getId() == null) {
            instance = object;
        } else {
            instance = doctorService.findById(object.getId());
        }

        binder.setBean(instance);
    }
}
