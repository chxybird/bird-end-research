import com.bird.PrometheusApp;
import com.bird.component.PromComponent;
import com.bird.component.PromUtils;
import com.bird.entity.ChartDataResult;
import com.bird.entity.PromResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author lipu
 * @Date 2021/11/2 14:27
 * @Description
 */
@SpringBootTest(classes = PrometheusApp.class)
public class NodeTest {

    @Resource
    private PromComponent promComponent;

    @Test
    void cpu() {
//        PromResult promResult = promComponent.calculateCpuUsage();
//        Map<String, List<ChartDataResult>> map = PromUtils.dealMatrixResult(promResult);
//        System.out.println(map);

    }
}
