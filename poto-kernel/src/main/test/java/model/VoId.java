package model;

import com.weibo.poto.entity.Identifier;
import lombok.AllArgsConstructor;

/**
 * @ClassName VoId
 * @Description TODO
 * @Author hebiao1
 * @Date 2021/9/6 5:33 下午
 * @Version 1.0
 */
@AllArgsConstructor
public class VoId extends Identifier {
    private Long id;
    @Override
    public Object getId() {
        return id;
    }
}
