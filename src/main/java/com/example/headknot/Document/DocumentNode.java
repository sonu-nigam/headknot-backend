package com.example.headknot.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentNode {
    private UUID id;
    private String title;
    private List<DocumentNode> children = new ArrayList<>();

    public DocumentNode(UUID id, String title) {
        this.title = title;
        this.id = id;
    }
}
