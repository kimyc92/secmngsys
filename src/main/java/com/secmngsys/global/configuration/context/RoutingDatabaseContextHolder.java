package com.secmngsys.global.configuration.context;

import com.secmngsys.global.configuration.code.DatabaseTypeCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class RoutingDatabaseContextHolder {

    private static final ThreadLocal<DatabaseTypeCode> contextHolder = new ThreadLocal<>();

    public static void set(DatabaseTypeCode databaseTypeCode) {
        System.out.println("셋팅!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("RoutingDatabaseContextHolder Set DB - "+databaseTypeCode);
        Assert.notNull(databaseTypeCode, "RoutingDatabase cannot be null");
        contextHolder.set(databaseTypeCode);
    }

    public static DatabaseTypeCode get() {
        log.debug("RoutingDatabaseContextHolder Get DB - "+contextHolder.get());
        if(contextHolder.get() == null)
            return DatabaseTypeCode.Master;

        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

}