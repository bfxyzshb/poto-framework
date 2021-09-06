package com.weibo.poto.repository.util;

import com.weibo.poto.entity.AggregateRoot;

import java.io.*;

public class SnapshotUtils {

    public static <T extends AggregateRoot> T snapshot(T aggregate)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(aggregate);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
    }
}
