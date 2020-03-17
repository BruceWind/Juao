package com.androidyuan.libcache.nativecache;

import com.androidyuan.libcache.core.ITicket;

/**
 * If memory exceeds the limit of the native pool, I will call {@OnFulledListener#onNeedRemove()}. Then i will move bytes to disk cache.
 *
 */
public interface OnFulledListener {

    void onNeedRomove(ITicket value ,byte[] bytes);

}
