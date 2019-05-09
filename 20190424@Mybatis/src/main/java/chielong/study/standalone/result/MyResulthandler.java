package chielong.study.standalone.result;

import lombok.Getter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 对返回的结果进行处理，最终得到自己想要的数据格式或类型
 * 可以自定义返回类型，在接口方法中传入，不需要定义
 *
 * Created by chielong on 2019-05-09.
 */
public class MyResulthandler implements ResultHandler {
    @Getter
    private final Map mappedResult = new HashMap();

    @Override
    public void handleResult(ResultContext resultContext) {
        Map map = (Map) resultContext.getResultObject();
        mappedResult.put(map.get("key") , map.get("value"));
    }
}
