package org.example.dto;

import java.util.List;

public record SearchResponse<T>(List<T> tList) {
}
