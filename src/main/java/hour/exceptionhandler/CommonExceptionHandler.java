package hour.exceptionhandler;

import hour.configure.LogConfig;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {

    /**
     *  拦截Exception类的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> exceptionHandler(Exception e)throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("status", false);
        result.put("message", e.getMessage());
        //正常开发中，可创建一个统一响应实体，如CommonResp
   //     throw e;
        LogConfig.LOG.info(e.getMessage());
        return result;
    }
}