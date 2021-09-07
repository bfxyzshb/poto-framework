package model;

import com.weibo.poto.entity.AbstractDomainObject;
import com.weibo.poto.entity.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.DiffInclude;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.ValueObject;

import java.util.List;


@AllArgsConstructor
public class Model extends AbstractDomainObject<ModelId> {

    private ModelId modelId;

    private String name;

    private Vo vo;
    @DiffInclude
    private List<Item> items;

    @Override
    public ModelId getId() {
        return modelId;
    }

    @Override
    public void setId(ModelId modelId) {

    }
}
