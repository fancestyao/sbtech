package com.example.sbtech.model;

import com.example.sbtech.enumeration.ItemStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "item_status")
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
    @Column(name = "item_name")
    private String itemName;
}
