package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.dto.ItemDTO;
import ntnu.idatt2105.project.backend.model.dto.Filter;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Item Service class which contains methods used to retrieve and handle items from the database.
 */
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemDTO getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid item id, item not found");
        }
        Item item = optionalItem.get();
        return new ItemDTO(item);
    }

    /**
     * Gets an item page, using the filter specified in the parameter. The method first gets the filtered
     * items from the database, then creates a Data Transfer Object for each of the items and maps them to a Page.
     * That page is returned.
     * @param pageNr
     * @param pageSize
     * @param filter
     * @return
     */
    public Page<Item> getItemPage(int pageNr, int pageSize, Filter filter) {
        int minPrice = filter.getMinPrice();
        int maxPrice = filter.getMaxPrice();
        long category = filter.getCategory();
        String search = filter.getSearch();
        return itemRepository.getItemsFiltered(minPrice, maxPrice, category, search, PageRequest.of(pageNr, pageSize));
    }

    public Page<Item> getItemsByUserIdPageable(String userId, Pageable pageable) {
        return itemRepository.findByUserId(userId, pageable);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

}
