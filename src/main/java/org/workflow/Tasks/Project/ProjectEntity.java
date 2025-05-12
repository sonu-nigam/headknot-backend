package org.workflow.Tasks.Project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.workflow.Shared.Entity.BaseEntity;

import java.util.UUID;

@Table(name = "project")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID projectId;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long totalTaskCount;

    @Column
    private Long status;
    // public String status;
    // public String createdAt;
    // public String updatedAt;
    // public String deletedAt;
    // public String createdBy;
    // public String updatedBy;
    // public String deletedBy;
    // public String members;
    // public String tags;
    // public String metadata;
    // public String settings;
    // public String tasks;
    // public String files;
    // public String comments;
    // public String activities;
    // public String reminders;
    // public String checklists;
    // public String timeTracking;
    // public String dependencies;
    // public String relatedItems;
    // public String subscribers;
    // public String customFields;
    // public String views;
    // public String integrations;
    // public String webhooks;
    // public String reports;
    // public String analytics;
    // public String integrationsData;
    // public String webhooksData;
    // public String reportsData;
    // public String analyticsData;
    // public String integrationsCount;
    // public String webhooksCount;
    // public String reportsCount;
    // public String analyticsCount;
    // public String integrationsEnabled;
    // public String webhooksEnabled;
    // public String reportsEnabled;
    // public String analyticsEnabled;
    // public String integrationsSettings;
    // public String webhooksSettings;
    // public String reportsSettings;
    // public String analyticsSettings;
    // public String integrationsDataCount;
    // public String webhooksDataCount;
}
