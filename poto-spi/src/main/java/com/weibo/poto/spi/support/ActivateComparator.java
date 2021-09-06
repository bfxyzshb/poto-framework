
package com.weibo.poto.spi.support;


import com.weibo.poto.spi.annotation.Activate;

import java.util.Comparator;

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
        Activate that = o1.getClass().getAnnotation(Activate.class);
        Activate anthor = o2.getClass().getAnnotation(Activate.class);
        return that.order() > anthor.order() ? -1 : 1;
    }

}
