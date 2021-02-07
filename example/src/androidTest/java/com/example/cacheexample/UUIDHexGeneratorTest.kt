package com.example.cacheexample

import com.androidyuan.libcache.core.UUIDHexGenerator
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * This class is used to test [UUIDHexGenerator] generate ID is unique in the world.
 */
class UUIDHexGeneratorTest {
    val generator = UUIDHexGenerator()

    @Test
    fun test() {
        val uuidList: MutableList<String> = ArrayList()
        for (index in 0..9999) {
            uuidList.add(generator.generate())
        }
        for (i in uuidList.indices) {
            for (j in i + 1 until uuidList.size) {
                Assert.assertNotEquals(uuidList[i], uuidList[j])
            }
        }
    }
}