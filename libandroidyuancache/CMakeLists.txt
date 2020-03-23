cmake_minimum_required(VERSION 3.4.1)           #普通版本的clang


add_library( # Sets the name of the library.
        cache

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/logger.h
        src/main/cpp/jnientry.cpp
        src/main/cpp/jnientry.h
        )

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

#set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} ${CMAKE_CODE_COVERAGE_LINKER_FLAGS} ")
target_link_libraries( # Specifies the target library.
        cache
        # included in the NDK.
        ${log-lib})
