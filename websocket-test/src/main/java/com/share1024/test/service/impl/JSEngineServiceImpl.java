package com.share1024.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.share1024.test.model.ConnectInfo;
import com.share1024.test.service.JSEngineService;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:56
 * \* Description:
 * \
 */
@Service
public class JSEngineServiceImpl implements JSEngineService {
    private static final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static final ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
    private static final String EXEC_METHOD = "gen";

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        String js = "function gen(){\n" +
                "    var list = new Array();\n" +
                "    list.push({\"id\":1,\"wsUrl\":\"1\"})\n" +
                "    list.push({\"id\":2,\"wsUrl\":\"2\"})\n" +
                "    return JSON.stringify(list);\n" +
                "}";
        scriptEngine.eval(js);
        Invocable jsInvoke = (Invocable) scriptEngine;
        try {
            Object obj = jsInvoke.invokeFunction(EXEC_METHOD);
            System.out.println(String.valueOf(obj));
            List<ConnectInfo> connectInfos = JSON.parseArray(String.valueOf(obj),ConnectInfo.class);
            System.out.println(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ConnectInfo> getBatchConnectUrl(String js) {
        try {
            scriptEngine.eval(js);
            Invocable jsInvoke = (Invocable) scriptEngine;
            Object obj = jsInvoke.invokeFunction(EXEC_METHOD,js);
            return JSON.parseArray(String.valueOf(obj),ConnectInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}