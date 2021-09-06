package com.weibo.poto.repository;

import com.weibo.poto.entity.AggregateRoot;
import com.weibo.poto.entity.Identifier;
import com.weibo.poto.repository.objectDiff.diff.EntityDiff;

public interface AggregateManager<T extends AggregateRoot<ID>, ID extends Identifier> {

    public static AggregateManager newInstance(Class targetClass) {
        ThreadLocalAggregateManager threadLocalAggregateManager = new ThreadLocalAggregateManager(targetClass);
        return threadLocalAggregateManager;
    }

    public void attach(T aggregate);

    public void attach(T aggregate, ID id);

    public void detach(T aggregate);

    public T find(ID id);

    public EntityDiff detectChanges(T aggregate);

    public void merge(T aggregate);
}
