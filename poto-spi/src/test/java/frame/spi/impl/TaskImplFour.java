package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import frame.spi.Task;

@Activate(value = {"taskFour"})
public class TaskImplFour implements Task {
    @Override
    public String getName() {
        return "TaskImplFour";
    }

    @Override
    public String getName(Protocol url) {
        return "four";
    }
}
