package frame.spi.impl;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.Activate;
import com.weibo.poto.spi.annotation.Adaptive;
import frame.spi.Task;

@Adaptive
@Activate
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
