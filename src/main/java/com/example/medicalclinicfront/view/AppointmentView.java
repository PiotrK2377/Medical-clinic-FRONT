package com.example.medicalclinicfront.view;

import com.example.medicalclinicfront.domain.AppointmentDto;
import com.example.medicalclinicfront.domain.AppointmentStatus;
import com.example.medicalclinicfront.service.AppointmentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import lombok.Data;

import java.util.List;


@Route("appointments")
public class AppointmentView extends VerticalLayout {

    private final AppointmentService appointmentService;
    private final Grid<AppointmentDto> grid = new Grid<>(AppointmentDto.class);
    private final ComboBox<AppointmentStatus> statusComboBox = new ComboBox<>("STATUS");
    private final DatePicker datePicker = new DatePicker("APPOINTMENT DATE");
    private final Button save = new Button("SAVE");
    private final Button delete = new Button("DELETE");
    private final Button readButton = new Button("REFRESH");
    private final Button backButton = new Button("BACK TO MAIN");
    private final Binder<AppointmentDto> binder = new Binder<>(AppointmentDto.class);
    private AppointmentDto currentAppointment;

    public AppointmentView(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        grid.setColumns("appointmentDate", "status");
        statusComboBox.setItems(AppointmentStatus.values());

        H1 pageTitle = new H1("Appointment Management");
        Paragraph pageDescription = new Paragraph("Manage appointments and their information.");

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        readButton.addClickListener(event -> refresh());
        backButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("/main")));

        //binder.bindInstanceFields(this);

        grid.asSingleSelect().addValueChangeListener(event -> {
            currentAppointment = event.getValue();
            binder.setBean(currentAppointment);
        });
        HorizontalLayout formLayout = new HorizontalLayout(datePicker,statusComboBox);
        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete, readButton, backButton);
        add(pageTitle, pageDescription, formLayout, buttonLayout, grid);
        setSizeFull();
        refresh();
    }

    private void save() {
        AppointmentDto appointmentDto = binder.getBean();
        appointmentService.createAppointment(appointmentDto);
        refresh();
        clearForm();
    }

    private void delete() {
        AppointmentDto appointmentDto = binder.getBean();
        if (appointmentDto.getId() != null) {
            appointmentService.deleteAppointment(appointmentDto.getId());
        }
        refresh();
        clearForm();
    }

    private void refresh() {
        try {
            List<AppointmentDto> appointmentDtos = appointmentService.getAllAppointments();
            grid.setItems(appointmentDtos);
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error");
        }
    }

    private void clearForm() {
        binder.setBean(new AppointmentDto());
        currentAppointment = null;
    }
}
