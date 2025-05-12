package org.workflow.Tasks.Status;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StatusEntity> getStatusList() {
        Iterable<StatusEntity> statusIterable = statusRepository.findAll(Sort.by(Sort.Direction.ASC, "statusOrder"));
        List<StatusEntity> statusList = new ArrayList<>();
        for (StatusEntity status : statusIterable) {
            statusList.add(status);
        }

        return statusList;
    }

    public Page<StatusEntity> getStatusList(Pageable pageable) {
        Page<StatusEntity> statusList = statusRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()));

        return statusList;
    }

    public StatusEntity getStatusByTitle(String title) {
        StatusEntity statusEntity = statusRepository.findByStatusName(title).orElse(null);
        if (statusEntity == null) {
            return null;
        }
        return statusEntity;
    }

    public StatusEntity getStatusById(UUID statusId) {
        StatusEntity statusEntity = statusRepository.findById(statusId).orElse(null);
        if (statusEntity == null) {
            return null;
        }
        return statusEntity;
    }


    public StatusEntity createStatus(StatusEntity status) {
        StatusEntity statusEntity = modelMapper.map(status, StatusEntity.class);
        return statusRepository.save(statusEntity);
    }

    public StatusEntity updateStatus(UUID statusId, StatusEntity status) {
        StatusEntity existingStatus = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + statusId));

        if (status.getStatusName() != null) {
            existingStatus.setStatusName(status.getStatusName());
        }

        return statusRepository.save(existingStatus);
    }

    public List<StatusEntity> updateStatusList(String projectSlug, List<UUID> updates) {
        List<StatusEntity> statusList = modelMapper.map(
                getStatusList(),
                new TypeToken<List<StatusEntity>>() {
                }.getType());
        List<StatusEntity> updatedStatusList = new ArrayList<>();

        for (int idx = 0; idx < updates.size(); idx++) {
            UUID statusId = updates.get(idx);
            StatusEntity status = statusList
                    .stream()
                    .filter(currentStatus -> currentStatus.getStatusId().equals(statusId))
                    .findFirst()
                    .get();

            if (status != null) {
                status.setStatusOrder(Long.valueOf(idx));
                updatedStatusList.add(status);
            }
        }

        Iterable<StatusEntity> updatedStatusEntityList = statusRepository.saveAll(updatedStatusList);
        return modelMapper.map(updatedStatusEntityList, new TypeToken<List<StatusEntity>>() {
        }.getType());
    }
}
