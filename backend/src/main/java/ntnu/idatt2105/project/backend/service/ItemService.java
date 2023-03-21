package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.Filter;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<Item> getItemPage(int pageNr, int pageSize, Filter filter){
        int minPrice = filter.getMinPrice();
        int maxPrice = filter.getMaxPrice();
        if(minPrice == 0 && maxPrice == 0){
            return itemRepository.getItems(PageRequest.of(pageNr, pageSize));
        }

        return itemRepository.getItemsByPrice(minPrice, maxPrice, PageRequest.of(pageNr, pageSize));
    }

}
