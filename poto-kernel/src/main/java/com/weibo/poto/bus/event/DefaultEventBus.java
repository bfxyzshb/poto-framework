package com.weibo.poto.bus.event;

import com.weibo.poto.bus.common.CommonMessage;
import com.weibo.poto.bus.common.MessageHandler;
import com.weibo.poto.logger.Logger;
import com.weibo.poto.logger.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultEventBus
 * @Description TODO
 * @Author hebiao1
 * @Date 2020/7/12 9:19 下午
 * @Version 1.0
 */
public class DefaultEventBus implements EventBus<EventMessage, Object> {
    private List<MessageHandler> handlers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(DefaultEventBus.class);

    @Override
    public void subscribe(MessageHandler<EventMessage> handler) {
        handlers.add(handler);
    }

    @Override
    public void dispatchAll(Object event) {
        handlers.forEach((h) -> {
            try {
                EventMessage eventTmp = CommonMessage.asEventMessage(event);
                h.handle(eventTmp);
            } catch (Throwable throwable) {
                logger.error("eventBus error", throwable);
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public Object dispatch(EventMessage event) {
        if (CollectionUtils.isEmpty(handlers)) {
            return null;
        }
        MessageHandler<EventMessage> messageHandler = handlers.get(0);
        EventMessage eventTmp = CommonMessage.asEventMessage(event);
        try {
            Object result = messageHandler.handle(eventTmp);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
