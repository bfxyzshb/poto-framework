package com.weibo.poto.repository.objectDiff.model;

import com.weibo.poto.repository.objectDiff.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item implements Entity {

    private long id;
    private String itemName;

}
