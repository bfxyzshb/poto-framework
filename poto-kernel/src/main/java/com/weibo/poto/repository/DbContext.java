package com.weibo.poto.repository;

import com.weibo.poto.entity.AggregateRoot;
import com.weibo.poto.entity.Identifier;
import com.weibo.poto.repository.objectDiff.diff.DiffUtils;
import com.weibo.poto.repository.objectDiff.diff.EntityDiff;
import com.weibo.poto.repository.util.ReflectionUtils;
import com.weibo.poto.repository.util.SnapshotUtils;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class DbContext<T extends AggregateRoot<ID>, ID extends Identifier> {

    @Getter
    private Class<? extends T> aggregateClass;

    private Map<ID, T> aggregateMap = new HashMap<>();

    public DbContext(Class<? extends T> aggregateClass) {
        this.aggregateClass = aggregateClass;
    }

    public void attach(T aggregate) {
        if (aggregate.getId() != null) {
            if (!aggregateMap.containsKey(aggregate.getId())) {
                this.merge(aggregate);
            }
        }
    }

    public void detach(T aggregate) {
        if (aggregate.getId() != null) {
            aggregateMap.remove(aggregate.getId());
        }
    }

    public EntityDiff detectChanges(T aggregate) {
        if (aggregate.getId() == null) {
            return EntityDiff.EMPTY;
        }
        T snapshot = aggregateMap.get(aggregate.getId());
        if (snapshot == null) {
            attach(aggregate);
        }
        return DiffUtils.diff(snapshot, aggregate);
    }

    public T find(ID id) {
        return aggregateMap.get(id);
    }

    public void merge(T aggregate) {
        if (aggregate.getId() != null) {
            T snapshot = null;
            try {
                snapshot = SnapshotUtils.snapshot(aggregate);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            aggregateMap.put(aggregate.getId(), snapshot);
        }
    }

    public void setId(T aggregate, ID id) {
        ReflectionUtils.writeField("id", aggregate, id);
    }
}