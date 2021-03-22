package me.border.utilities.cache;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CachedObject implements Cacheable {
    public final Object object;
    private long timeOfExpiration = -1;

    private int hash = 0;

    public CachedObject(Object obj){
        this(obj, 30, TimeUnit.MINUTES);
    }

    public CachedObject(Object obj, int timeToLiveMinutes){
        this(obj, timeToLiveMinutes, TimeUnit.MINUTES);
    }

    public CachedObject(Object obj, long timeToLive, TimeUnit tu) {
        Objects.requireNonNull(obj, "Object cannot be null");
        this.object = obj;

        if (timeToLive != 0) {
            long millisToLive = tu.toMillis(timeToLive);
            this.timeOfExpiration = System.currentTimeMillis() + millisToLive;
        }
    }

    @Override
    public boolean isExpired() {
        if (timeOfExpiration == -1)
            return false;
        return timeOfExpiration <= System.currentTimeMillis();
    }

    @Override
    public void add(long time, TimeUnit tu){
        this.timeOfExpiration += tu.toMillis(time);
    }

    @Override
    public Object getObject(){
        return object;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o instanceof Cacheable){
            Cacheable cacheable = (Cacheable) o;
            return cacheable.getObject().equals(this.getObject());
        }

        return false;
    }

    @Override
    public int hashCode(){
        if (this.hash == 0){
            this.hash = 59 * getObject().hashCode();
        }

        return hash;
    }
}
