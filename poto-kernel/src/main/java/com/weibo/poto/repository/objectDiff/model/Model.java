package com.weibo.poto.repository.objectDiff.model;

import com.weibo.poto.repository.objectDiff.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Model implements Entity {

    private long id;

    private String name;

    private Vo vo;

    private List<Item> items;

}
