package ntnu.idatt2105.project.backend;

import ntnu.idatt2105.project.backend.model.Item;
import ntnu.idatt2105.project.backend.model.ItemImage;
import ntnu.idatt2105.project.backend.repository.ItemImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

@Profile("dev")
@Component
public class StartupImageInitializer {

    @Autowired
    private ItemImageRepository itemImageRepository;

    @PostConstruct
    public void init() throws IOException {
        insertImages();
    }
    Logger logger = LoggerFactory.getLogger(StartupImageInitializer.class);
    private void insertImages() throws IOException {
        String[] imagePaths = {"redshirt.jpg", "harry.jpg", "smartphone.jpg", "sofa.jpg" , "tennisracket.jpg", "legoset.jpg"};
        Long i = 0L;
        for (String path : imagePaths) {
            i++;
            logger.info("Inserting image: " + path);
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/" + path));
            byte[] data = inputStream.readAllBytes();


            ItemImage itemImage = new ItemImage();
            itemImage.setData(data);
            Item item = new Item();
            item.setId(i);
            itemImage.setItem(item);
            itemImage.setContentType("image/jpeg");

            itemImageRepository.save(itemImage);
        }
    }
}