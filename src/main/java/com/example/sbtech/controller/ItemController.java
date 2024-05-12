package com.example.sbtech.controller;

import com.example.sbtech.dto.ItemOutDTO;
import com.example.sbtech.model.Item;
import com.example.sbtech.service.interfaces.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Item API")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @Operation(summary = "Save item",
               description = "Saves an item and returns the dto version of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is alright"),
    })
    public ResponseEntity<ItemOutDTO> saveItem(@RequestBody @Parameter(description = "Data of item to save into DB") Item item) {
        log.info("ItemController: Item to save request");
        return new ResponseEntity<>(itemService.saveItem(item), HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    @Operation(summary = "Update item",
               description = "Updates an item and returns the dto version of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is alright"),
            @ApiResponse(responseCode = "404", description = "Item is not in DB")
    })
    public ResponseEntity<ItemOutDTO> updateItem(@PathVariable @Parameter(description = "Id of an item to update") Long itemId,
                                 @RequestBody @Parameter(description = "Data of new item to save into db") Item newItem) {
        log.info("ItemController: Item to update request");
        return new ResponseEntity<>(itemService.updateItem(itemId, newItem), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete item",
               description = "Deletes an item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is alright"),
            @ApiResponse(responseCode = "404", description = "Item is not in DB")
    })
    public void deleteItem(@PathVariable @Parameter(description = "Id of an item to delete") Long itemId) {
        log.info("ItemController: Item to delete request");
        itemService.deleteItem(itemId);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Get item",
               description = "Finds an item in DB and returns the dto version of it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is alright"),
            @ApiResponse(responseCode = "404", description = "Item is not in DB")
    })
    public ResponseEntity<ItemOutDTO> getItem(@PathVariable @Parameter(description = "Id of an item to delete") Long itemId) {
        log.info("ItemController: Item to get request");
        return new ResponseEntity<>(itemService.getItem(itemId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all items",
               description = "Finds all items in DB and returns the list of dto versions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Everything is alright")
    })
    public ResponseEntity<List<ItemOutDTO>> getItems() {
        log.info("ItemController: List of items to get request");
        return new ResponseEntity<>(itemService.getListOfItems(), HttpStatus.OK);
    }
}
