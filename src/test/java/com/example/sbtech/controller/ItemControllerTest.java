package com.example.sbtech.controller;

import com.example.sbtech.dto.ItemOutDTO;
import com.example.sbtech.enumeration.ItemStatus;
import com.example.sbtech.model.Item;
import com.example.sbtech.service.interfaces.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test item controller")
public class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;
    @Mock
    private ItemService itemService;

    @Test
    @DisplayName("Test save item")
    void saveItemViaController_ReturnResponseEntityWithItemOutDTO() {
        Item item = Item
                .builder()
                .itemId(1L)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();
        ItemOutDTO itemOutDTO = ItemOutDTO
                .builder()
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();
        doReturn(itemOutDTO).when(this.itemService).saveItem(item);

        ResponseEntity<ItemOutDTO> responseEntity = this.itemController.saveItem(item);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(itemOutDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test update item")
    void updateItemViaController_ReturnResponseEntityWithItemOutDTO() {
        Long itemId = 1L;
        Item updatedItem = Item
                .builder()
                .itemId(itemId)
                .itemStatus(ItemStatus.SOLD)
                .itemName("UpdatedItemNameOne")
                .build();
        ItemOutDTO updatedItemOutDTO = ItemOutDTO
                .builder()
                .itemStatus(ItemStatus.SOLD)
                .itemName("UpdatedItemNameOne")
                .build();
        doReturn(updatedItemOutDTO).when(this.itemService).updateItem(itemId, updatedItem);

        ResponseEntity<ItemOutDTO> responseEntity = this.itemController.updateItem(itemId, updatedItem);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedItemOutDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test delete item")
    void saveItem_checkIfNotNull_thenSaveAgainAndDelete_checkIfNull() {
        Long itemId = 1L;
        Item item = Item
                .builder()
                .itemId(itemId)
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();
        ItemOutDTO savedItem = itemController.saveItem(item).getBody();
        ItemOutDTO returnedItem = itemController.getItem(itemId).getBody();
        assertThat(savedItem).isEqualTo(returnedItem);
        itemController.deleteItem(itemId);
        assertThat(returnedItem).isNull();
    }

    @Test
    @DisplayName("Test get all items")
    void callGetListOfItems_thenReturnListOfItemOutDTO() {
        ItemOutDTO item1 = ItemOutDTO
                .builder()
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameOne")
                .build();
        ItemOutDTO item2 = ItemOutDTO
                .builder()
                .itemStatus(ItemStatus.INDELIVERY)
                .itemName("ItemNameTwo")
                .build();
        List<ItemOutDTO> list = List.of(item1, item2);
        when(itemService.getListOfItems()).thenReturn(list);
        ResponseEntity<List<ItemOutDTO>> response = itemController.getItems();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
        verify(itemService).getListOfItems();
    }
}
