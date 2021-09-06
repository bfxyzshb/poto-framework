package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
<<<<<<< HEAD
import com.weibo.poto.spi.annotation.Adaptive;
import frame.spi.Task;


//@Adaptive({"taskTwo"})
@Activate(value = {"taskTwo"})
=======
import frame.spi.Task;

/**
 * Created by 278076999@qq.com 2018/8/15 14:36
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
//@Adaptive({"taskTwo"})
@Activate(value = {"taskTwo"}, group = {"alicdn"})
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
public class TaskImplTwo implements Task {
    @Override
    public String getName() {
        return "TaskImplTwo";
    }

    @Override
    public String getName(Protocol url) {
        return "two";
    }
}
