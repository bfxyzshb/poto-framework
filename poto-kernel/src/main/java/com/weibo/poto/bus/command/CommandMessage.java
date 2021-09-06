package com.weibo.poto.bus.command;

import com.weibo.poto.domain.Message;

public interface CommandMessage extends Message {

    String getCommandName();

    

}