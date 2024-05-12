package com.example.sbtech.service;

import com.example.sbtech.dto.ItemOutDTO;
import com.example.sbtech.enumeration.ItemStatus;
import com.example.sbtech.model.Item;
import com.example.sbtech.repository.ItemRepository;
import com.example.sbtech.service.classes.ItemServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test item service")
public class ItemServiceTest {
    @InjectMocks
    private ItemServiceImpl itemService;
    @Mock
    private ItemRepository itemRepository;

    @Test
    void whenGetItemViaItemService_thenRepositoryShouldBeCalledOnce() {
        Long itemId = 1L;
        Item item = Item
                .builder()
                .itemId(itemId)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();

        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item));

        itemService.getItem(item.getItemId());
        verify(itemRepository, times(1)).findById(any());
    }

    @Test
    void whenGetItemsViaItemService_thenRepositoryShouldBeCalledOnce() {
        Long itemOneId = 1L;
        Long itemTwoId = 2L;
        Item item = Item
                .builder()
                .itemId(itemOneId)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();
        Item item2 = Item
                .builder()
                .itemId(itemTwoId)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();

        List<Item> items = List.of(item, item2);

        when(itemRepository.findAll()).thenReturn(items);

        itemService.getListOfItems();
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void whenSaveItemViaItemService_thenRepositoryShouldBeCalledOnce() {
        Long itemId = 1L;
        Item item = Item
                .builder()
                .itemId(itemId)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();

        when(itemRepository.save(any())).thenReturn(item);

        itemService.saveItem(item);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    public void whenUpdatedItem_ThenVerifyThatItemIsUpdated() {
        Long itemId = 1L;
        Item newItem = new Item();
        newItem.setItemName("ItemOneName");
        newItem.setItemStatus(ItemStatus.INDELIVERY);

        Item oldItem = new Item();
        oldItem.setItemId(itemId);
        oldItem.setItemName("UpdatedItemOneName");
        oldItem.setItemStatus(ItemStatus.SOLD);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(oldItem));
        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemOutDTO result = itemService.updateItem(itemId, newItem);

        assertEquals(newItem.getItemName(), result.getItemName());
        assertEquals(newItem.getItemStatus(), result.getItemStatus());

        verify(itemRepository).findById(itemId);
        verify(itemRepository).save(oldItem);
    }

    @Test
    public void whenItemIsGiven_thenDeleteItemById() {
        Long itemId = 1L;
        Item testItem = new Item();
        testItem.setItemId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(testItem));

        itemService.deleteItem(itemId);

        verify(itemRepository).findById(itemId);
        verify(itemRepository).delete(testItem);
    }
}
