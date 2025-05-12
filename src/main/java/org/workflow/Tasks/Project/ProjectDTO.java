package org.workflow.Tasks.Project;

import java.util.UUID;

import lombok.Data;

@Data
public class ProjectDTO {
    public UUID projectId;
    public String projectName;
    public String slug;
    public String description;
    public Long totalTaskCount;
    public String status;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;
    public String createdBy;
    public String updatedBy;
    public String deletedBy;
    public String members;
    public String tags;
    public String metadata;
    public String settings;
    public String tasks;
    public String files;
    public String comments;
    public String activities;
    public String reminders;
    // public String checklists;
    public String timeTracking;
    // public String dependencies;
    // public String relatedItems;
    public String subscribers;

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
