package com.example.medicalclinicfront.view;

import com.example.medicalclinicfront.domain.Rating;
import com.example.medicalclinicfront.domain.ReviewDto;
import com.example.medicalclinicfront.service.ReviewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

@Route("reviews")
public class ReviewView extends VerticalLayout {

    private final ReviewService reviewService;
    private final Grid<ReviewDto> grid = new Grid<>(ReviewDto.class);
    private final ComboBox<Rating> rating = new ComboBox<>("RATING");
    private final TextField comment = new TextField("COMMENT");
    private final Button save = new Button("SAVE");
    private final Button delete = new Button("DELETE");
    private final Button readButton = new Button("REFRESH");
    private final Button backButton = new Button("BACK TO MAIN");
    private final Binder<ReviewDto> binder = new Binder<>(ReviewDto.class);
    private ReviewDto currentReview;

    public ReviewView(ReviewService reviewService) {
        this.reviewService = reviewService;
        grid.setColumns("rating", "comment");
        rating.setItems(Rating.values());

        H1 pageTitle = new H1("Review Management");
        Paragraph pageDescription = new Paragraph("Manage reviews and their information.");

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        readButton.addClickListener(event -> refresh());
        backButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("/main")));

        binder.bindInstanceFields(this);

        grid.asSingleSelect().addValueChangeListener(event -> {
            currentReview = event.getValue();
            binder.setBean(currentReview);
        });

        HorizontalLayout formLayout = new HorizontalLayout(rating, comment);
        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete, readButton, backButton);
        add(pageTitle, pageDescription, formLayout, buttonLayout,grid);
        setSizeFull();
        refresh();
    }

    private void save() {
        ReviewDto reviewDto = binder.getBean();
        reviewService.createReview(reviewDto);
        clearForm();
        refresh();
    }

    private void delete() {
        ReviewDto reviewDto = binder.getBean();
        if (reviewDto.getId() != null) {
            reviewService.deleteReview(reviewDto.getId());
        }
        clearForm();
        refresh();
    }

    private void refresh() {
        try {
            List<ReviewDto> reviewDtos = reviewService.getAllReviews();
            if (reviewDtos.isEmpty()) {
                grid.setItems(Collections.emptyList());
                Notification.show("No review available");
            } else {
                grid.setItems(reviewDtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error");
        }
    }

    private void clearForm() {
        binder.setBean(new ReviewDto());
        currentReview = null;
    }
}
