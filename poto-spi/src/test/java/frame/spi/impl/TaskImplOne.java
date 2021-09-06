package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import com.weibo.poto.spi.annotation.Adaptive;
import frame.spi.Task;

<<<<<<< HEAD
//@Adaptive
@Activate( group = {"alicdn"})
=======
/**
 * Created by 278076999@qq.com 2018/8/15 14:36
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
@Adaptive
@Activate
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
public class TaskImplOne implements Task {
    @Override
    public String getName() {
        return "TaskImplOne";
    }

    @Override
    public String getName(Protocol url) {
        return "one";
    }
}
