package ru.pasha.views.components.editors;

import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pasha.Constants;
import ru.pasha.entity.Doctor;
import ru.pasha.entity.Patient;
import ru.pasha.entity.Recipe;
import ru.pasha.entity.RecipePriority;
import ru.pasha.services.DoctorService;
import ru.pasha.services.PatientService;
import ru.pasha.services.RecipeService;

import java.sql.Date;
import java.time.LocalDate;


@SpringComponent
@UIScope
public class RecipeEditor extends EntityEditor<Recipe> {

    private static class SqlDateToLocalDateConverter implements Converter<LocalDate, Date> {
        @Override
        public Result<Date> convertToModel(LocalDate value, ValueContext context) {
            if (value == null) {
                return Result.ok(null);
            }
            return Result.ok(Date.valueOf(value));
        }

        @Override
        public LocalDate convertToPresentation(Date value, ValueContext context) {
            if (value == null) {
                return null;
            }
            return value.toLocalDate();
        }
    }

    private final RecipeService recipeService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    private TextArea description;
    private ComboBox<Patient> patient;
    private ComboBox<Doctor> doctor;
    private ComboBox<RecipePriority> priority;
    private DateField creationDate, validity;


    @Autowired
    public RecipeEditor(RecipeService recipeService, PatientService patientService, DoctorService doctorService) {
        super();
        this.recipeService = recipeService;
        this.patientService = patientService;
        this.doctorService = doctorService;

        binder = new Binder<>(Recipe.class);

        patient = new ComboBox<>(Constants.PATIENT_RU_TEXT);
        patient.setEmptySelectionAllowed(false);
        patient.setDataProvider(this.patientService::fetch, this.patientService::count);

        doctor = new ComboBox<>(Constants.DOCTOR_RU_TEXT);
        doctor.setEmptySelectionAllowed(false);
        doctor.setDataProvider(this.doctorService::fetch, this.doctorService::count);

        priority = new ComboBox<>(Constants.PRIORITY_RU_TEXT);
        priority.setEmptySelectionAllowed(false);
        priority.setItems(RecipePriority.values());
        priority.setItemCaptionGenerator(RecipePriority::name);

        creationDate = new DateField(Constants.CREATION_DATE_RU_TEXT);
        validity = new DateField(Constants.VALIDITY_RU_TEXT);

        description = new TextArea(Constants.DESCRIPTION_RU_TEXT, "");

        binder
                .forField(patient)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .bind("patient");

        binder
                .forField(doctor)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .bind("doctor");

        binder
                .forField(priority)
                .asRequired(Constants.FILL_ALL_FIELDS_ERROR)
                .bind("priority");

        binder
                .forField(creationDate)
                .asRequired()
                .withValidator(date -> date.isAfter(LocalDate.now().minusDays(1)), Constants.SELECT_PAST_DATE_ERROR)
                .withConverter(new SqlDateToLocalDateConverter())
                .bind("creationDate");

        binder
                .forField(validity)
                .asRequired()
                .withValidator(date -> date.isAfter(creationDate.getValue()), Constants.VALIDITY_BEFORE_CREATION_ERROR)
                .withConverter(new SqlDateToLocalDateConverter())
                .bind("validity");

        binder
                .forField(description)
                .withValidator(new StringLengthValidator(
                        Constants.RECIPE_DESCRIPTION_LENGTH_ERROR,
                        Constants.RECIPE_DESCRIPTION_MIN_LENGTH,
                        Constants.STRING_MAX_LENGTH))
                .bind("description");

        binder.bindInstanceFields(this);

        mainLayout.addComponents(doctor, patient, priority, creationDate, validity, description);
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
        recipeService.save(instance);
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    @Override
    public void edit(Recipe object) {
        if (object == null) {
            return;
        }

        if (object.getId() == null) {
            instance = object;
            instance.setCreationDate(Date.valueOf(LocalDate.now()));
            instance.setValidity(Date.valueOf(LocalDate.now().plusDays(7)));
        } else {
            instance = recipeService.findById(object.getId());
        }

        binder.setBean(instance);
    }

}
