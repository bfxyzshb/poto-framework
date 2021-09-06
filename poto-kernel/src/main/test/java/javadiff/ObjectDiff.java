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

        Model working = new Model(new ModelId(1l), "name", new Vo(1, "desc"), Lists.newArrayList(new Item(new ItemId(1L), "name1"), new Item(new ItemId(3L), "name")));
        Model base = new Model(new ModelId(1l), "name", new Vo(1, "desc1"), Lists.newArrayList(new Item(new ItemId(1L), "name"), new Item(new ItemId(2L), "name")));

        final EntityDiff entityDiff = DiffUtils.diff(base, working);

        final Diff diff = entityDiff.getDiff("items");

        if (diff instanceof DiffList) {
            DiffList dl = (DiffList) diff;
            for (Diff d : dl) {
                System.err.println(d.getType());
                System.err.println(JSON.toJSONString(d.getNewValue()));
                System.err.println(JSON.toJSONString(d.getOldVlaue()));
            }
        }
    }

}
