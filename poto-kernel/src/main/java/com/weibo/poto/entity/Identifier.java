package com.weibo.poto.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class Identifier<T> implements Serializable {

    public abstract T getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier identifier = (Identifier) o;
        return Objects.equals(this.getId(), identifier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
