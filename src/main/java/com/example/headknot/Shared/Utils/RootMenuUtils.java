package com.example.headknot.Shared.Utils;

import com.example.headknot.Shared.Enum.RootMenuEnum;

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
