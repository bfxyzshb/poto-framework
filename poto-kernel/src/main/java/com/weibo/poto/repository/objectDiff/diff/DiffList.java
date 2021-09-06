package com.weibo.poto.repository.objectDiff.diff;

import java.util.ArrayList;
import java.util.List;

public class DiffList extends ArrayList<Diff> implements Diff, List<Diff> {

    @Override
    public DiffType getType() {
        return null;
    }

    @Override
    public Object getNewValue() {
        return null;
    }

    @Override
    public Object getOldVlaue() {
        return null;
    }

}
