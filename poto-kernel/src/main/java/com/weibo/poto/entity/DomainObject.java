package com.weibo.poto.entity;

import java.io.Serializable;

public interface DomainObject<ID extends Identifier> extends Serializable {
    ID getId();

    void setId(ID id);
}
