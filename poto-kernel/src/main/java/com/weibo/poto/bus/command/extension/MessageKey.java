package com.weibo.poto.bus.command.extension;

public abstract class MessageKey {
    public MessageKey() {
    }

    public abstract int hashCode();

    public abstract boolean equals(Object var1);

    public abstract String toString();
}