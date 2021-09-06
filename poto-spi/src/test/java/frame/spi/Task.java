package frame.spi;

import com.weibo.poto.spi.Protocol;
import com.weibo.poto.spi.annotation.SPI;

@SPI
public interface Task {

    String getName();

    String getName(Protocol url);
}
