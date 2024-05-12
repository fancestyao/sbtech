package com.example.sbtech.service.interfaces;

import com.example.sbtech.dto.ItemOutDTO;
import com.example.sbtech.model.Item;

import java.util.List;

public interface ItemService {
    ItemOutDTO saveItem(Item item);

    ItemOutDTO updateItem(Long itemId, Item newItem);

    void deleteItem(Long itemId);

    ItemOutDTO getItem(Long itemId);

    List<ItemOutDTO> getListOfItems();
}
