package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import frame.spi.Task;


@Activate( group = {"alicdn"})
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
