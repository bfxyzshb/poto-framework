package model;

import com.weibo.poto.entity.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Model implements DomainObject<ModelId> {

    private ModelId modelId;

    private String name;

    private Vo vo;

    private List<Item> items;

    @Override
    public ModelId getId() {
        return modelId;
    }

    @Override
    public void setId(ModelId modelId) {

    }
}
