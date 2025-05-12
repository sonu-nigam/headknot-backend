package org.workflow.Tasks.Priority;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PriorityService {
    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PriorityDTO> getPriorityList() {
        Iterable<PriorityEntity> priorityList = priorityRepository.findAll();

        List<PriorityDTO> priorityDTOList = new ArrayList<>();
        for (PriorityEntity priority : priorityList) {
            PriorityDTO priorityDTO = modelMapper.map(priority, PriorityDTO.class);
            priorityDTOList.add(priorityDTO);
        }

        return priorityDTOList;
    }

    public PriorityEntity getPriorityById(UUID priorityId) {
        PriorityEntity priority = priorityRepository.findById(priorityId).orElse(null);
        if (priority == null) {
            return null;
        }
        return priority;
    }

    public PriorityEntity getPriorityByTitle(String title) {
        PriorityEntity priority = priorityRepository.findByTitle(title).orElse(null);
        if (priority == null) {
            return null;
        }
        return priority;
    }
}
