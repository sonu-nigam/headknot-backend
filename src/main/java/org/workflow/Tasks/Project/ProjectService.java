package org.workflow.Tasks.Project;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<ProjectEntity> getProjectList(Pageable pageable) {
        Page<ProjectEntity> projectList = projectRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
        ));

        return projectList;
    }

    public ProjectEntity getProjectById(UUID projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId).orElse(null);
        if (projectEntity == null) {
            return null;
        }
        return projectEntity;
    }

    public ProjectEntity getProjectBySlug(String slug) {
        ProjectEntity projectEntity = projectRepository.findBySlug(slug).orElse(null);
        if (projectEntity == null) {
            return null;
        }
        return projectEntity;
    }

    public ProjectEntity createProject(ProjectCreateRequestDTO project) {
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return savedProject;
    }

    public ProjectEntity updateProject(String slug, ProjectCreateRequestDTO updates) {
        ProjectEntity existingProject = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + slug));

        if (updates.getProjectName() != null) {
            existingProject.setProjectName(updates.getProjectName());
        }

        ProjectEntity updatedProject = projectRepository.save(existingProject);
        return updatedProject;
    }
}
