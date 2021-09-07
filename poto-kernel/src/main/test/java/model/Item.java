package model;

import com.weibo.poto.entity.AbstractDomainObject;
import com.weibo.poto.entity.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class Item extends AbstractDomainObject<ItemId> {

    private ItemId id;
    private String itemName;

    @Override
    public ItemId getId() {
        return id;
    }

    @Override
    public void setId(ItemId itemId) {
        this.id = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
