package com.example.L3.entity;

import com.example.L3.constant.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDate deadline;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "assignedTo")
    private Employee assignedTo;

    @PrePersist
    @PreUpdate
    private void normalizeDeadline() {
        if (this.deadline != null) {
            this.deadline = LocalDate.of(
                    deadline.getYear(),
                    deadline.getMonth(),
                    deadline.getDayOfMonth()
            );
        }
    }
}
