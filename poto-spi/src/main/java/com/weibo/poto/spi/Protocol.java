package com.weibo.poto.spi;

import java.util.Set;

/**
 * 抽象协议,由业务方自己扩展
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
public interface Protocol {

    Set<String> getParameters();

}
