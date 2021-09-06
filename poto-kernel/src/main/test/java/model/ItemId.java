package model;

import com.weibo.poto.entity.Identifier;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * @ClassName ItemId
 * @Description TODO
 * @Author hebiao1
 * @Date 2021/9/6 3:40 下午
 * @Version 1.0
 */
@AllArgsConstructor
public class ItemId extends Identifier<Long> {
    private Long id;


    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemId itemId = (ItemId) o;
        return Objects.equals(id, itemId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
