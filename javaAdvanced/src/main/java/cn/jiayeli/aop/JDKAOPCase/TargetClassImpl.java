package cn.jiayeli.aop.JDKAOPCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetClassImpl implements TargetInterface{
    Logger log = LoggerFactory.getLogger(TargetClassImpl.class);

    @Override
    public int hasException() {
        return 1/0;
    }

    @Override
    public void nonException() {
        log.error("nonException");
    }
}
