
package com.weibo.poto.spi.support;


import com.weibo.poto.spi.annotation.Activate;

import java.util.Comparator;

<<<<<<< HEAD

=======
/**
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
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
<<<<<<< HEAD
        Activate that = o1.getClass().getAnnotation(Activate.class);
        Activate anthor = o2.getClass().getAnnotation(Activate.class);
=======

        /**
         * to support com.alibab.dubbo.common.extension.Activate
         */
        Activate that = o1.getClass().getAnnotation(Activate.class);
        Activate anthor = o2.getClass().getAnnotation(Activate.class);
        /**
         * never return 0 even if n1 equals n2,
         * otherwise, o1 and o2 will override each other in collection like HashSet
         */
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
        return that.order() > anthor.order() ? -1 : 1;
    }

}
