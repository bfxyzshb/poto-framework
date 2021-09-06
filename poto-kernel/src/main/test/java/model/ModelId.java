package model;

import com.weibo.poto.entity.Identifier;
import lombok.AllArgsConstructor;

/**
 * @ClassName Id
 * @Description TODO
 * @Author hebiao1
 * @Date 2021/9/6 3:34 下午
 * @Version 1.0
 */
@AllArgsConstructor
public class ModelId extends Identifier<Long> {
    Long id;

    @Override
    public Long getId() {
        return id;
    }
}
