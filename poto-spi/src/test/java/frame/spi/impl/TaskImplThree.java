package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import frame.spi.Task;

<<<<<<< HEAD

@Activate( group = {"alicdn"})
=======
/**
 * Created by 278076999@qq.com 2018/8/15 14:36
 *
 * @author <a href="mailto:278076999@qq.com">simple</a>
 */
@Activate(value = {"taskThree"}, group = {"alicdn"})
>>>>>>> 29fcd689d547cfa23f566b17e13de6e12429067b
public class TaskImplThree implements Task {
    @Override
    public String getName() {
        return "TaskImplThree";
    }

    @Override
    public String getName(Protocol url) {
        return "three";
    }
}
