package com.example.headknot.Shared.DTO;

import com.example.headknot.Shared.Enum.RootMenuEnum;

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
