package com.weibo.poto.bus.command;

import com.weibo.poto.bus.common.DTO;
import com.weibo.poto.spi.Protocol;

/**
 * @ClassName Command
 * @Author hebiao1
 * @Date 2021/9/2 3:04 下午
 * @Version 1.0
 */
public abstract class Command extends DTO {
    private Protocol protocol;

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
