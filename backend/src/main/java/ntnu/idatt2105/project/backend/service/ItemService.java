package ntnu.idatt2105.project.backend.service;

import lombok.RequiredArgsConstructor;
import ntnu.idatt2105.project.backend.model.dto.ItemDTO;
import ntnu.idatt2105.project.backend.model.dto.Filter;
import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<ItemDTO> getItemPage(int pageNr, int pageSize, Filter filter){
        int minPrice = filter.getMinPrice();
        int maxPrice = filter.getMaxPrice();
        Page<Item> itemPage;
        if(minPrice == 0 && maxPrice == 0){
            itemPage = itemRepository
                    .getItems(PageRequest.of(pageNr, pageSize));
        }
        else{
            itemPage = itemRepository
                    .getItemsByPrice(minPrice, maxPrice, PageRequest.of(pageNr, pageSize));
        }
        Page<ItemDTO> copyPage = itemPage.map(item -> {
            ItemDTO itemDTO = new ItemDTO(item);
            return itemDTO;
        });
        Page<ItemDTO> pageDTO = new PageImpl<>(copyPage.getContent(), copyPage.getPageable(), copyPage.getTotalElements());

        return pageDTO;




    }

}
