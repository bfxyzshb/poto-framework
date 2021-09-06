package model;

import com.weibo.poto.entity.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item implements DomainObject<ItemId> {

    private ItemId id;
    private String itemName;

    @Override
    public void setId(ItemId itemId) {

    }
}
