package com.weibo.poto.repository.objectDiff.diff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiffObject implements Diff {

    private DiffType diffType;

    private Object newValue;

    private Object oldValue;

    @Override
    public DiffType getType() {
        return diffType;
    }

    @Override
    public Object getNewValue() {
        return newValue;
    }

    @Override
    public Object getOldVlaue() {
        return oldValue;
    }

}
