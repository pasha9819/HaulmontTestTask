package ru.pasha;

import com.vaadin.data.validator.StringLengthValidator;

public interface Constants {

    String FILL_ALL_FIELDS_ERROR = "Заполните все поля";
    String CHECK_FIELD_FILLING_CORRECTION = "Проверьте корректность заполнения полей";
    String STRING_MAX_LENGTH_ERROR = "Длина поля не может превышать 255 символов";

    String VALIDITY_BEFORE_CREATION_ERROR = "Срок действия должен быть позже дня создания";
    String SELECT_PAST_DATE_ERROR = "Нельзя выбрать прошедшую дату";

    int RECIPE_DESCRIPTION_MIN_LENGTH = 3;
    int STRING_MAX_LENGTH = 255;

    String RECIPE_DESCRIPTION_LENGTH_ERROR = "Длина описания должна составлять от " +
            RECIPE_DESCRIPTION_MIN_LENGTH + " до " + STRING_MAX_LENGTH + " символов";

    int PHONE_NUMBER_MIN_LENGTH = 3;
    int PHONE_NUMBER_MAX_LENGTH = 14;

    String PHONE_NUMBER_LENGTH_ERROR = "Длина номера телефона должна составлять от " +
            PHONE_NUMBER_MIN_LENGTH + " до " + PHONE_NUMBER_MAX_LENGTH + " символов";

    StringLengthValidator STRING_LENGTH_DEFAULT_VALIDATOR = new StringLengthValidator(STRING_MAX_LENGTH_ERROR,
            1,STRING_MAX_LENGTH);

    String DOCTOR_RU_TEXT = "Врач";
    String PATIENT_RU_TEXT = "Пациент";
    String CREATION_DATE_RU_TEXT = "Дата создания";
    String VALIDITY_RU_TEXT = "Срок действия";
    String PRIORITY_RU_TEXT = "Приоритет";
    String DESCRIPTION_RU_TEXT = "Описание";
    String ADD_RU_TEXT = "Добавить";
    String DELETE_RU_TEXT = "Удалить";
    String EDIT_RU_TEXT = "Изменить";
    String FILTER_BUTTON_RU_TEXT = "Применить фильтры";
    String SURNAME_RU_TEXT = "Фамилия";
    String NAME_RU_TEXT = "Имя";
    String PATRONYMIC_RU_TEXT = "Отчество";
    String NUMBER_RU_TEXT = "Номер телефона";
    String SPECIALIZATION_RU_TEXT = "Специализация";
    String OPEN_STATISTIC_RU_TEXT = "Показать статистику";
    String CLOSE_STATISTIC_RU_TEXT = "Скрыть статистику";
    String PATIENT_HAS_RECIPES_ERROR = "Пациента нельзя удалить, так как у него есть рецепты";
    String DOCTOR_HAS_RECIPES_ERROR = "Врача нельзя удалить, так как у него есть рецепты";
    String OK_BUTTON_RU_TEXT = "ОК";
    String CANCEL_BUTTON_RU_TEXT = "Отмена";
}
