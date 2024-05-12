package com.example.sbtech.service.classes;

import com.example.sbtech.dto.ItemOutDTO;
import com.example.sbtech.exception.NotFoundException;
import com.example.sbtech.model.Item;
import com.example.sbtech.repository.ItemRepository;
import com.example.sbtech.service.interfaces.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemOutDTO saveItem(Item item) {
        log.info("ItemServiceImpl: Item save request");
        Item savedItem = itemRepository.save(item);
        log.info("Item: {} was successfully saved into DB", savedItem);
        return ItemOutDTO
                .builder()
                .itemName(savedItem.getItemName())
                .itemStatus(savedItem.getItemStatus())
                .build();
    }

    @Override
    public ItemOutDTO updateItem(Long itemId, Item newItem) {
        log.info("ItemServiceImpl: Item to update has id: {}, new data to fill: {}", itemId, newItem);
        Item oldItem = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " +
                        itemId + " was not found"));
        log.info("Old item is: {}", oldItem);
        oldItem.setItemName(newItem.getItemName());
        oldItem.setItemStatus(newItem.getItemStatus());
        Item savedItem = itemRepository.save(oldItem);
        log.info("Item: {} was successfully updated and saved into DB", savedItem);
        return ItemOutDTO
                .builder()
                .itemName(savedItem.getItemName())
                .itemStatus(savedItem.getItemStatus())
                .build();
    }

    @Override
    public void deleteItem(Long itemId) {
        log.info("ItemServiceImpl: Item to delete has id: {}", itemId);
        Item itemToDelete = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " +
                        itemId + " was not found"));
        log.info("Item to delete is: {}", itemToDelete);
        itemRepository.delete(itemToDelete);
        log.info("Item with id: {} was successfully deleted", itemId);
    }

    @Override
    public ItemOutDTO getItem(Long itemId) {
        log.info("ItemServiceImpl: Item to get has id: {}", itemId);
        Item itemToGet = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item with id: " +
                        itemId + " was not found"));
        log.info("Item to get is: {}", itemToGet);
        return ItemOutDTO
                .builder()
                .itemName(itemToGet.getItemName())
                .itemStatus(itemToGet.getItemStatus())
                .build();
    }

    @Override
    public List<ItemOutDTO> getListOfItems() {
        log.info("ItemServiceImpl: request to get all items from DB");
        List<ItemOutDTO> itemOutDTOList = itemRepository.findAll()
                .stream()
                .map(item -> ItemOutDTO
                        .builder()
                        .itemName(item.getItemName())
                        .itemStatus(item.getItemStatus())
                        .build())
                .collect(Collectors.toList());
        log.info("ItemServiceImpl: list of items: {}", itemOutDTOList);
        return itemOutDTOList;
    }
}