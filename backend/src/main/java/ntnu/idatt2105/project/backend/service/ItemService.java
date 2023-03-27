package ntnu.idatt2105.project.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.*;
import ntnu.idatt2105.project.backend.model.dto.ItemDTO;
import ntnu.idatt2105.project.backend.model.dto.Filter;
import ntnu.idatt2105.project.backend.model.enums.Role;
import ntnu.idatt2105.project.backend.repository.BookmarkRepository;
import ntnu.idatt2105.project.backend.repository.ChatRepository;
import ntnu.idatt2105.project.backend.repository.ItemImageRepository;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Item Service class which contains methods used to retrieve and handle items from the database.
 */
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ItemImageRepository itemImageRepository;
    private final ChatRepository chatRepository;

    public ItemDTO getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid item id, item not found");
        }
        Item item = optionalItem.get();
        return new ItemDTO(item);
    }

    public ItemDTO deleteItem(Long id, User user){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid item id, item not found");
        }
        Item item = optionalItem.get();
        if(item.getUser().getId() != user.getId() && user.getRole() != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not owner of item or admin");
        }
        List<Bookmark> bookmarks = bookmarkRepository.findAllByItemId(id);
        bookmarks.forEach(bookmarkRepository::delete);
        List<ItemImage> itemImages = itemImageRepository.findAllByItemId(id);
        itemImages.forEach(itemImageRepository::delete);
        List<Chat> chats = chatRepository.findAllByItemId(item.getId());
        chats.forEach(chatRepository::delete);

        itemRepository.delete(item);
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
        Double longitude = filter.getLongitude();
        Double latitude = filter.getLatitude();
        Double maxDistance = filter.getMaxDistance();

        Page<Item> page = itemRepository.getItemsFiltered(minPrice, maxPrice, category, search, PageRequest.of(pageNr, pageSize));

        if (longitude != null && latitude != null && maxDistance != null) {
            List<Item> filteredItems = page.getContent()
                    .stream()
                    .filter(item -> distance(latitude, longitude, item.getLatitude(), item.getLongitude()) <= maxDistance)
                    .collect(Collectors.toList());
            page = new PageImpl<>(filteredItems, PageRequest.of(pageNr, pageSize), filteredItems.size());
        }

        return page;
    }

    public Page<Item> getItemsByUserIdPageable(String userId, Pageable pageable) {
        return itemRepository.findByUserId(userId, pageable);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public ItemDTO editItem(Item newItem){
        Item oldItem = itemRepository.findById(newItem.getId()).orElse(null);
        if(oldItem != null && (newItem.getUser() == oldItem.getUser() || newItem.getUser().getRole() == Role.ADMIN)){
            if(newItem.getImages().size() == 0){
                newItem.setImages(oldItem.getImages());
            }
            itemRepository.save(newItem);
            return new ItemDTO(newItem);
        }
        return null;


    }

    /**
     * Calculates the distance between two coordinates using the haversine formula.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6371 * c; // Radius of the earth in km
    }
}
