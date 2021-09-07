package javadiff;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.weibo.poto.repository.objectDiff.diff.Diff;
import com.weibo.poto.repository.objectDiff.diff.DiffList;
import com.weibo.poto.repository.objectDiff.diff.DiffUtils;
import com.weibo.poto.repository.objectDiff.diff.EntityDiff;
import model.*;
import org.junit.Test;


public class ObjectDiff {
    @Test
    public void test() {
        Model base = new Model(new ModelId(1l), "name",new Vo(new VoId(1L), "desc"), Lists.newArrayList(new Item(new ItemId(1L), "name")));
        Model working = new Model(new ModelId(1l), "name",new Vo(new VoId(1L), "desc1"),  Lists.newArrayList(new Item(new ItemId(1L), "name1")));


        final EntityDiff entityDiff = DiffUtils.diff(base, working);
        //System.out.println(entityDiff.getDiff("vo").getNewValue());
        final Diff diff = entityDiff.getDiff("items");

        if (diff instanceof DiffList) {
            DiffList dl = (DiffList) diff;
            for (Diff d : dl) {
                System.err.println(d.getType());
                System.err.println(d.getNewValue());
                System.err.println(JSON.toJSONString(d.getOldVlaue()));
            }
        }
    }

}
