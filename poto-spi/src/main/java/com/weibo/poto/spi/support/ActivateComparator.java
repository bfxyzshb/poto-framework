
package com.weibo.poto.spi.support;


import com.weibo.poto.spi.annotation.Activate;

import java.util.Comparator;

/**
 * Created by lvxiang 2019/3/2  17:47
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
public class ActivateComparator implements Comparator<Object> {
    public static final Comparator<Object> COMPARATOR = new ActivateComparator();

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        if (o1.equals(o2)) {
            return 0;
        }

        /**
         * to support com.alibab.dubbo.common.extension.Activate
         */
        Activate that = o1.getClass().getAnnotation(Activate.class);
        Activate anthor = o2.getClass().getAnnotation(Activate.class);
        /**
         * never return 0 even if n1 equals n2,
         * otherwise, o1 and o2 will override each other in collection like HashSet
         */
        return that.order() > anthor.order() ? -1 : 1;
    }

}
