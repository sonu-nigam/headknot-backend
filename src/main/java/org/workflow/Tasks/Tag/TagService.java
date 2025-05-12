package org.workflow.Tasks.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workflow.Tasks.Project.ProjectEntity;
import org.workflow.Tasks.Project.ProjectService;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    public List<TagEntity> getTagListByTagIdList(Set<UUID> tagIdList) {
        List<TagEntity> tagList = tagRepository.findAllByTagIdIn(tagIdList);
        return tagList;
    }

    public List<TagEntity> getTagListByProjectId(UUID projectId) {
        return tagRepository.findAllByProjectId(projectId);
    }

    public List<TagWithSelectedKeyDTO> tagWithSelectedKeyList(List<TagEntity> tagList, List<TagEntity> selectedTags) {
        List<TagWithSelectedKeyDTO> tagListWithSelectedTags = new ArrayList<>();
        for (TagEntity tag : tagList) {
            TagWithSelectedKeyDTO tagWithSelectedKey = modelMapper.map(tag, TagWithSelectedKeyDTO.class);
            tagWithSelectedKey.setSelected(false);
            TagEntity selectedTag = selectedTags.stream().filter(currentTag -> {
                return currentTag.tagId == tag.tagId;
            }).findAny().orElse(null);
            boolean isTagSelected = false;
            if (selectedTag != null)
                isTagSelected = true;
            tagWithSelectedKey.setSelected(isTagSelected);
            tagListWithSelectedTags.add(tagWithSelectedKey);
        }
        return tagListWithSelectedTags;
    }

    public TagEntity createTag(TagCreateRequestDTO tagDTO, String projectSlug) {
        ProjectEntity currentProject = projectService.getProjectBySlug(projectSlug);
        tagDTO.setProjectId(currentProject.getProjectId());

        TagEntity tagEntity = modelMapper.map(tagDTO, TagEntity.class);
        return tagRepository.save(tagEntity);
    }
}
