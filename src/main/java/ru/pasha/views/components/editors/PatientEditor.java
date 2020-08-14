package ru.pasha.views.components.editors;

import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasha.Constants;
import ru.pasha.entity.Patient;
import ru.pasha.services.PatientService;

@SpringComponent
@UIScope
public class PatientEditor extends EntityEditor<Patient> {
    private final PatientService patientService;

    private TextField name, patronymic, surname, number;

    @Autowired
    public PatientEditor(PatientService patientService) {
        super();
        this.patientService = patientService;

        binder = new Binder<>(Patient.class);

        surname = new TextField(Constants.SURNAME_RU_TEXT, "");
        name = new TextField(Constants.NAME_RU_TEXT, "");
        patronymic = new TextField(Constants.PATRONYMIC_RU_TEXT, "");
        number = new TextField(Constants.NUMBER_RU_TEXT, "");

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
                .forField(number)
                .asRequired(Constants.PHONE_NUMBER_LENGTH_ERROR)
                .withValidator(this::numberValidator, Constants.PHONE_NUMBER_LENGTH_ERROR)
                .bind("number");

        binder.bindInstanceFields(this);

        mainLayout.addComponents(surname, name, patronymic, number);
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
        patientService.save(instance);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    @Override
    public void edit(Patient object) {
        if (object == null) {
            return;
        }

        if (object.getId() == null) {
            instance = object;
        } else {
            instance = patientService.findById(object.getId());
        }

        binder.setBean(instance);
    }

    private boolean numberValidator(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d{" + Constants.PHONE_NUMBER_MIN_LENGTH + ","
                + Constants.PHONE_NUMBER_MAX_LENGTH + "}");
    }

}
