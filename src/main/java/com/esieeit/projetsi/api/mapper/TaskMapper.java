package com.esieeit.projetsi.api.mapper;

import com.esieeit.projetsi.api.dto.TaskResponse;
import com.esieeit.projetsi.domain.model.Task;

public final class TaskMapper {

    private TaskMapper() {
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name());
    }
}
