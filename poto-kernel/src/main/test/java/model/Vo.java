package model;

import com.weibo.poto.entity.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vo implements DomainObject<VoId> {

    private VoId id;
    private String desc;



    @Override
    public void setId(VoId voId) {

    }
}
