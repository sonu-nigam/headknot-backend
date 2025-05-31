package com.example.headknot.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCreateRequestDTO {
    private UUID id;
    private UUID parent;
    private String title;
    private String description;
}
