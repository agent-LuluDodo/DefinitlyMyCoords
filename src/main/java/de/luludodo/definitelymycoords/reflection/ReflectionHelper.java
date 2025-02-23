package de.luludodo.definitelymycoords.reflection;

import de.luludodo.definitelymycoords.DefinitelyMyCoords;

import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper {
    @FunctionalInterface
    public interface ClassGetter {
        Class<?> get() throws Exception;
    }

    private Class<?> cl = null;
    private Object cur = null;
    private final String errorMessage;
    private boolean errored = false;
    public ReflectionHelper(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void set(ClassGetter getter) {
        try {
            this.cl = getter.get();
            this.cur = null;
        } catch (Exception e) {
            onError(e);
        }
    }

    public void set(Class<?> cl) {
        this.cl = cl;
        this.cur = null;
    }

    public void set(Object instance) {
        if (instance == null) {
            onError(new NullPointerException("instance is null"));
            return;
        }

        this.cl = instance.getClass();
        this.cur = instance;
    }

    public void instance(String name) {
        if (check())
            return;

        try {
            cur = cl.getField(name).get(cur);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            onError(e);
        }
    }

    public <T> T get(String name, Class<T> target) {
        if (check())
            return null;

        Object value;
        try {
            value = cl.getField(name).get(cur);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            onError(e);
            return null;
        }

        try {
            return target.cast(value);
        } catch (ClassCastException e) {
            onError(e);
            return null;
        }
    }

    public void getInstance(String name, Object... params) {
        getInstance(
                name,
                getParamCls(
                        params,
                        "getInstance(String name, Class<?>[] paramCls, Object... params)"
                ),
                params
        );
    }

    public void getInstance(String name, Class<?>[] paramCls, Object... params) {
        if (check())
            return;

        if (paramCls.length != params.length) {
            onError(new IllegalArgumentException("paramCls.length != params.length"));
            return;
        }

        try {
            cur = cl.getMethod(name, paramCls).invoke(cur, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            onError(e);
        }
    }

    public void call(String name, Object... params) {
        call(
                name,
                getParamCls(
                        params,
                "call(String name, Class<?>[] paramCls, Object... params)"
                ),
                params
        );
    }

    public void call(String name, Class<?>[] paramCls, Object... params) {
        if (check())
            return;

        if (paramCls.length != params.length) {
            onError(new IllegalArgumentException("paramCls.length != params.length"));
            return;
        }

        try {
            cl.getMethod(name, paramCls).invoke(cur, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            onError(e);
        }
    }

    public <T> T call(String name, Class<T> returnCl, Object... params) {
        return call(
                name,
                getParamCls(
                        params,
                        "call(String name, Class<?>[] paramCls, Class<T> returnCl, Object... params)"
                ),
                returnCl,
                params
        );
    }

    public <T> T call(String name, Class<?>[] paramCls, Class<T> returnCl, Object... params) {
        if (check())
            return null;

        if (paramCls.length != params.length) {
            onError(new IllegalArgumentException("paramCls.length != params.length"));
            return null;
        }

        Object result;
        try {
            result = cl.getMethod(name, paramCls).invoke(cur, params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            onError(e);
            return null;
        }

        try {
            return returnCl.cast(result);
        } catch (ClassCastException e) {
            onError(e);
            return null;
        }
    }

    public void newInstance(Object... params) {
        newInstance(
                getParamCls(
                        params,
                        "newInstance(Class<?>[] paramCls, Object... params)"
                ),
                params
        );
    }

    public void newInstance(Class<?>[] paramCls, Object... params) {
        if (check())
            return;

        if (paramCls.length != params.length) {
            onError(new IllegalArgumentException("paramCls.length != params.length"));
            return;
        }

        try {
            cur = cl.getConstructor(paramCls).newInstance(params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            onError(e);
        }
    }

    public <T> T constructor(Class<T> cl, Object... params) {
        return constructor(
                getParamCls(
                        params,
                        "constructor(Class<?>[] paramCls, Class<T> cl, Object... params)"
                ),
                cl,
                params
        );
    }

    public <T> T constructor(Class<?>[] paramCls, Class<T> cl, Object... params) {
        if (check())
            return null;

        if (paramCls.length != params.length) {
            onError(new IllegalArgumentException("paramCls.length != params.length"));
            return null;
        }

        Object instance;
        try {
            instance = cl.getConstructor(paramCls).newInstance(params);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            onError(e);
            return null;
        }

        try {
            return cl.cast(instance);
        } catch (ClassCastException e) {
            onError(e);
            return null;
        }
    }

    private Class<?>[] getParamCls(Object[] params, String altMethod) {
        Class<?>[] paramCls = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];

            if (param == null) {
                onError(new NullPointerException("To allow nullable params use " + altMethod + " instead"));
                return null;
            }

            paramCls[i] = param.getClass();
        }
        return paramCls;
    }

    private boolean check() {
        if (cl == null) {
            onError(new NullPointerException());
        }
        return hasErrors();
    }

    public boolean hasErrors() {
        return errored;
    }

    public void clearErrors() {
        errored = false;
    }

    public void onError(Exception e) {
        if (!errored) {
            DefinitelyMyCoords.LOG.error("ReflectionHelper | {}", errorMessage, e);
            errored = true;
        }
    }
}
