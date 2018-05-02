package org.spongepowered.mctester.framework.proxy;

import org.spongepowered.api.scheduler.SpongeExecutorService;
import org.spongepowered.mctester.McTester;

import java.util.concurrent.TimeUnit;

public class MainThreadProxy extends BaseProxy {

    public MainThreadProxy(Object realObject, SpongeExecutorService mainThreadExecutor, ProxyCallback callback) {
        super(realObject, mainThreadExecutor, callback);
    }

    public static boolean shouldProxy(Object object) {
        return object != null
                && (object.getClass().getName().startsWith("org.spongepowered") || object.getClass().getName().startsWith("net.minecraft")) // Proxy anything from Sponge or Minecraft...
                && !(object.getClass().getName().equals(BaseProxy.class.getName())); // unless it's another proxy, because that would be dumb
    }

    public static <T> T newProxy(T object, ProxyCallback callback) {
        if (shouldProxy(object)) {
            MainThreadProxy proxy = new MainThreadProxy(object, McTester.INSTANCE.syncExecutor, callback);
            return (T) proxy.makeProxy(object.getClass());
        }
        return object;
    }

    @Override
    Object dispatch(InvocationData data) {

        SpongeExecutorService.SpongeFuture<Object> future = this.mainThreadExecutor.schedule(data, 0, TimeUnit.SECONDS);

        // Blocking
        Object result;
        try {
            result = future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Object proxied = MainThreadProxy.newProxy(result, this.callback);
        return proxied;
    }
}
