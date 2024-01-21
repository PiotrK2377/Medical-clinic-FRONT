package com.example.medicalclinicfront.view;


import com.example.medicalclinicfront.domain.DoctorDto;
import com.example.medicalclinicfront.domain.DoctorSpecialization;
import com.example.medicalclinicfront.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;


@Route("doctors")
public class DoctorView extends VerticalLayout {

    private final DoctorService doctorService;
    private final Grid<DoctorDto> grid = new Grid<>(DoctorDto.class);
    private final TextField name = new TextField("NAME");
    private final TextField lastname = new TextField("LASTNAME");
    private final ComboBox<DoctorSpecialization> specialization = new ComboBox<>("SPECIALIZATION");
    private final TextField numberPWZ = new TextField("PWZ NUMBER");
    private final Button save = new Button("SAVE");
    private final Button delete = new Button("DELETE");
    private final Button readButton = new Button("REFRESH");
    private final Button backButton = new Button("BACK TO MAIN");
    private final Binder<DoctorDto> binder = new Binder<>(DoctorDto.class);
    private DoctorDto currentDoctor;
    private final RestTemplate restTemplate;


    public DoctorView(DoctorService doctorService, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.doctorService = doctorService;

        H1 pageTitle = new H1("Doctor Management");
        Paragraph pageDescription = new Paragraph("Manage doctors and their information.");

        grid.setColumns("name", "lastname", "specialization", "numberPWZ");
        specialization.setItems(DoctorSpecialization.values());
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        readButton.addClickListener(event -> refresh());
        backButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("/main")));

        binder.bindInstanceFields(this);

        grid.asSingleSelect().addValueChangeListener(event -> {
            currentDoctor = event.getValue();
            binder.setBean(currentDoctor);
        });
        HorizontalLayout formLayout = new HorizontalLayout(name, lastname, specialization, numberPWZ);
        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete, readButton, backButton);
        add(pageTitle, pageDescription, formLayout, buttonLayout, grid);
        setSizeFull();
        refresh();

    }

    private void save() {
        DoctorDto doctorDto = binder.getBean();
        doctorService.createDoctor(doctorDto);
        refresh();
        clearForm();

    }

    private void delete() {
        DoctorDto doctorDto = binder.getBean();
        if (doctorDto.getId() != null) {
            doctorService.deleteDoctor(doctorDto.getId());
        }
        clearForm();
        refresh();
    }

    private void refresh() {
        try {
            List<DoctorDto> doctorDtos = doctorService.getAllDoctors();
            if (doctorDtos.isEmpty()) {
                grid.setItems(Collections.emptyList());
                Notification.show("No doctor available.");
            } else {
                grid.setItems(doctorDtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error");
        }

    }

    private void clearForm() {
        binder.setBean(new DoctorDto());
        currentDoctor = null;
    }

}
