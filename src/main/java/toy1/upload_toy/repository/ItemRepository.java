package toy1.upload_toy.repository;

import org.springframework.stereotype.Repository;
import toy1.upload_toy.domain.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private long sequence = 0L;

    public Item save(Item item) {
        item.setItemId(++sequence);
        store.put(item.getItemId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }
}
