package com.weibo.poto.spi;

<<<<<<< HEAD
import java.util.HashSet;
=======
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
import java.util.Set;

/**
 * 抽象协议,由业务方自己扩展
<<<<<<< HEAD
 */
public interface Protocol {

    Set<String> parameters = new HashSet<>();

    default void addParameter(String parameter) {
        parameters.add(parameter);
    }

    default Set<String> getParameters() {
        return parameters;
    }

    String getExtensionName();
=======
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
public interface Protocol {

    Set<String> getParameters();
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b

}
