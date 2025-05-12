package org.workflow.Utils;

import java.util.HashMap;

public class TaskUtility {
    public static String getTaskId(Long taskId) {
        if (taskId == null) {
            return null;
        }
        return String.format("T%06d", taskId);
    }

    public static Long getIdFromTaskId(String taskId) {
        if (taskId == null || taskId.isEmpty()) {
            return null;
        }
        return Long.parseLong(taskId.split("-")[1]);
    }

    public static String getProjectSlugFromTaskSlug(String taskSlug) {
        if (taskSlug == null || taskSlug.isEmpty()) {
            return null;
        }
        return taskSlug.split("-")[0];
    }

    public static ParsedTaskSlug parseTaskSlug(String taskSlug) {
        if (taskSlug == null || taskSlug.isEmpty()) {
            return null;
        }

        Long taskId = Long.parseLong(taskSlug.split("-")[1]);
        String projectSlug = taskSlug.split("-")[0];

        return new ParsedTaskSlug(projectSlug, taskId);
    }

    public static String buildTaskSlug(String projectSlug, Long taskId) {
        String taskSlug = projectSlug + taskId.toString();
        return taskSlug;
    }
}
