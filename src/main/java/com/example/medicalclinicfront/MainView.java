package com.example.medicalclinicfront;

import com.example.medicalclinicfront.view.AppointmentView;
import com.example.medicalclinicfront.view.DoctorView;
import com.example.medicalclinicfront.view.ReviewView;
import com.example.medicalclinicfront.view.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("main")
public class MainView extends VerticalLayout {

    public MainView() {
        Tabs tabs = new Tabs(
                createTab("Appointments", AppointmentView.class),
                createTab("Doctors", DoctorView.class),
                createTab("Reviews", ReviewView.class),
                createTab("Users", UserView.class)
        );


        add(createHeader(), tabs);
    }

    private VerticalLayout createHeader() {
        VerticalLayout header = new VerticalLayout();
        header.setAlignItems(Alignment.CENTER);

        header.add("Welcome to the Medical Clinic Database!");
        header.add(" Find out more about doctors, users, appointments and reviews.");
        return header;
    }

    private Tab createTab(String label, Class<? extends Component> viewClass) {
        RouterLink link = new RouterLink(label, viewClass);
        Tab tab = new Tab(link);
        tab.setClassName("main-tab");
        return tab;
    }
}
