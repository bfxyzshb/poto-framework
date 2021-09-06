package com.weibo.poto.repository.objectDiff.javadiff;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.weibo.poto.repository.objectDiff.diff.Diff;
import com.weibo.poto.repository.objectDiff.diff.DiffList;
import com.weibo.poto.repository.objectDiff.diff.DiffUtils;
import com.weibo.poto.repository.objectDiff.diff.EntityDiff;
import com.weibo.poto.repository.objectDiff.model.Item;
import com.weibo.poto.repository.objectDiff.model.Model;
import com.weibo.poto.repository.objectDiff.model.Vo;


/**
 * https://github.com/SQiShER/java-object-diff
 */
public class ObjectDiff {

    public static void main(String[] args) {

        Model working = new Model(1,"name",new Vo(1,"desc"), Lists.newArrayList(new Item(1, "name1"),new Item(3, "name")));
        Model base = new Model(1,"name",new Vo(1,"desc1"), Lists.newArrayList(new Item(1, "name"),new Item(2, "name")));

        final EntityDiff entityDiff = DiffUtils.diff(base, working);

        final Diff diff = entityDiff.getDiff("items");

        if (diff instanceof DiffList) {
            DiffList dl = (DiffList)diff;
            for (Diff d : dl) {
                System.err.println(d.getType());
                System.err.println(JSON.toJSONString(d.getNewValue()));
                System.err.println(JSON.toJSONString(d.getOldVlaue()));
            }
        }
    }

}
