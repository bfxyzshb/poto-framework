package com.weibo.poto.bus.command.extension;

import com.weibo.poto.bus.common.DTO;
import lombok.Getter;
import lombok.Setter;

public abstract class Command extends DTO {
    @Getter
    @Setter
    MessageKey messageKey;

    public Command(MessageKey messageKey) {
        this.messageKey = messageKey;
    }
}
