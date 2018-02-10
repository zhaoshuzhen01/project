package com.example.baselibrary.photomanager;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A synchronization lock that creates a separate lock for each key.
 * Created by xiaobaima on 15-11-15.
 */
public class KeyedLock<K> {
    private static final String TAG = KeyedLock.class.getSimpleName();
    private static boolean DEBUG = false;
    private final Map<K, ReentrantLock> mLocks = new HashMap<K, ReentrantLock>();

    /**
     * @param key
     */
    public void lock(K key) {
        if (DEBUG) {
            log("acquiring lock for key " + key);
        }

        ReentrantLock lock;
        synchronized (mLocks) {
            lock = mLocks.get(key);
            if (lock == null) {
                lock = new ReentrantLock();
                mLocks.put(key, lock);
                if (DEBUG) {
                    log(lock + " created new lock and added it to map");
                }

            }
        }

        lock.lock();
    }

    /**
     * @param key
     */
    public void unlock(K key) {
        if (DEBUG) {
            log("unlocking lock for key " + key);
        }
        ReentrantLock lock;

        synchronized (mLocks) {
            lock = mLocks.get(key);
            if (lock == null) {
                Log.e(TAG, "Attempting to unlock lock for key " + key + " which has no entry");
                return;
            }
            if (DEBUG) {
                log(lock + " has queued threads " + lock.hasQueuedThreads() + " for key " + key);
            }
            // maybe entries should be removed when there are no queued threads. This would
            // occasionally fail...
            // final boolean queued = lock.hasQueuedThreads();

            lock.unlock();
        }
    }

    private void log(String message) {

        Log.d(TAG, Thread.currentThread().getId() + "\t" + message);

    }
}
