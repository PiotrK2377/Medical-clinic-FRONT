package com.example.medicalclinicfront.view;

import com.example.medicalclinicfront.domain.UserDto;
import com.example.medicalclinicfront.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;

@Route("users")
public class UserView extends VerticalLayout {

    private final UserService userService;
    private final Grid<UserDto> grid = new Grid<>(UserDto.class);
    private final TextField name = new TextField("NAME");
    private final TextField lastname = new TextField("LASTNAME");
    private final TextField peselNumber = new TextField("PESEL NUMBER");
    private final TextField email = new TextField("EMAIL");
    private final TextField phoneNumber = new TextField("PHONE NUMBER");
    private final Button save = new Button("SAVE");
    private final Button delete = new Button("DELETE");
    private final Button readButton = new Button("REFRESH");
    private final Button backButton = new Button("BACK TO MAIN");
    private final Binder<UserDto> binder = new Binder<UserDto>(UserDto.class);
    private UserDto currentUser;


    public UserView(UserService userService) {
        this.userService = userService;
        grid.setColumns("name", "lastname", "peselNumber", "email", "phoneNumber");

        H1 pageTitle = new H1("User Management");
        Paragraph pageDescription = new Paragraph("Manage users and their information.");

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        readButton.addClickListener(event -> refresh());
        backButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("/main")));

        binder.bindInstanceFields(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            currentUser = event.getValue();
            binder.setBean(currentUser);
        });

        HorizontalLayout formLayout = new HorizontalLayout(name, lastname, peselNumber, email, phoneNumber);
        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete, readButton, backButton);
        add(pageTitle, pageDescription, formLayout, buttonLayout, grid);
        setSizeFull();
        refresh();
    }

    private void save() {
        UserDto userDto = binder.getBean();
        userService.createUser(userDto);
        refresh();
        clearForm();
    }

    private void delete() {
        UserDto userDto = binder.getBean();
        if (userDto.getId() != null) {
            userService.deleteUser(userDto.getId());
        }
        refresh();
        clearForm();
    }

    private void refresh() {
        try {
            List<UserDto> userDtos = userService.getAllUsers();
            if (userDtos.isEmpty()) {
                grid.setItems(Collections.emptyList());
                Notification.show("No user available");
            } else {
                grid.setItems(userDtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error");
        }
    }

    private void clearForm() {
        binder.setBean(new UserDto());
        currentUser = null;
    }
}
