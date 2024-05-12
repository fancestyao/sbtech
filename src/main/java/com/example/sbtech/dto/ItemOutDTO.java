package com.example.sbtech.dto;

import com.example.sbtech.enumeration.ItemStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOutDTO {
    private ItemStatus itemStatus;
    private String itemName;
}
