package org.workflow.Shared.DTO;

import org.workflow.Shared.Enum.RootMenuEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RootMenu {

    public RootMenu() {
    }

    public List<String> getMenuItems() {
        return Arrays.stream(RootMenuEnum.values()).map(rootMenuEnum -> rootMenuEnum.value).collect(Collectors.toList());
    }
}
