package com.example.headknot.Shared.Entity;

import com.example.headknot.Shared.Enum.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_status_bkp")
public class TaskStatusEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    public UUID taskstatusid;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TaskStatusEnum status;
}