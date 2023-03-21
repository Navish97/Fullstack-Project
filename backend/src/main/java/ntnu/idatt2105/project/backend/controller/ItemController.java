package ntnu.idatt2105.project.backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Filter;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import ntnu.idatt2105.project.backend.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
@Tag(name = "Item Controller", description = "Controller to handle items")
public class ItemController {
    Logger logger = LoggerFactory.getLogger(ItemController.class);
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
            @RequestParam final Integer pageNumber,
            @RequestParam final Integer size,
            @RequestParam String filter) throws JsonProcessingException {
                logger.info("Received api call for retrieving a page of items. Page: " + pageNumber + " Page size: " + size + " with filter: " + filter);
                Filter f = this.parseFilter(filter);

        return ResponseEntity.ok(generateResponse(itemService.getItemPage(pageNumber, size, f)));
    }

    private Filter parseFilter(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Filter filter = objectMapper.readValue(json, Filter.class);
        return filter;
    }
}
