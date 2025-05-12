package org.workflow.Shared.Utils;

import org.workflow.Shared.Enum.RootMenuEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RootMenuUtils {

    public RootMenuUtils() {
    }

    public static List<RootMenuEnum> getMenuItems() {
        return Arrays.stream(RootMenuEnum.values()).map(rootMenuEnum -> rootMenuEnum).collect(Collectors.toList());
    }
}
