package com.androidyuan.libcache.core;


/**
 * .--------.
 * (   Start  )
 * '---------'
 * |
 * |
 * v
 * ______________                ______________           ______________
 * \             \               \             \          \             \
 * )   Native    )-------------->)   Disk      )--------->)    Lost     )----------------------------------.
 * /_____________/               /_____________/          /_____________/                                   |
 * |                              |                                           ___________            v
 * |                              |                                           \           \       .---------.
 * |                              |                                            ） RELEASED )----->(   END    )
 * |                              |                                           /___________/       '---------'
 * |                              |                                                 ^
 * |                              |                ______________                   |
 * |                              v                \             \                  |
 * '---------------------------------------------->)   Resume    )-----------------'
 * /_____________/
 */

public final class TicketStatus {

    public static final int CACHE_STATUS_ON_CACHING = 0;
    public static final int CACHE_STATUS_ON_NATIVE = 1;
    public static final int CACHE_STATUS_ONDISK = 2;
    public static final int CACHE_STATUS_IS_RESUMED = 3;
    public static final int CACHE_STATUS_WAS_LOST = -1;
    public static final int CACHE_STATUS_HAS_RELEASED = -2;

}
