package com.weibo.poto.spi;

import java.util.Map;

/**
 * Created by 278076999@qq.com 2018/11/7 10:42
 * 抽象协议,由业务方自己扩展
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
public interface Protocol {
    String getParameter(String key);

    boolean hasParameter(String key);

    String getParameter(String key, String defaultValue);

    Map<String, String> getParameters();

}
