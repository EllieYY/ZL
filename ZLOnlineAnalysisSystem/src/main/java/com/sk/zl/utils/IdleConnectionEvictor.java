package com.sk.zl.utils;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description : 定期关闭失效连接
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Component
public class IdleConnectionEvictor extends Thread {
    @Autowired
    private HttpClientConnectionManager connectionManager;
    private volatile boolean shutdown;

    public IdleConnectionEvictor() {
        super();
        super.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    connectionManager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
