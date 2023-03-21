package ntnu.idatt2105.project.backend.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import ntnu.idatt2105.project.backend.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Item Controller", description = "Controller to handle items")
public class ItemController {

    private final ItemService itemService;

    private Map<String, Object> generateResponse(final Page<Item> page){
        Map<String, Object> response = new HashMap<>();
        response.put("items", page.getContent());
        response.put("current-page", page.getNumber());
        response.put("total-items", page.getTotalElements());
        response.put("total-pages", page.getTotalPages());
        return response;
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPersonsPageable(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "15") final Integer size) {
        return ResponseEntity.ok(generateResponse(itemService.getItemPage(pageNumber, size)));
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }
}
