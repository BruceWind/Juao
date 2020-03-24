package com.androidyuan.libcache;

import com.androidyuan.libcache.core.ITicket;

/**
 * This class is used to pop data asynchronously.
 */
public interface OnPopCompleteListener {
    void onCompleted(ITicket it);
}
