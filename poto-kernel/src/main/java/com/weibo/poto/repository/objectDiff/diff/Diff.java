package com.weibo.poto.repository.objectDiff.diff;

public interface Diff {

    DiffType getType();

    Object getNewValue();

    Object getOldVlaue();

}
