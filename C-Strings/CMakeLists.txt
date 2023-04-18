cmake_minimum_required(VERSION 3.23)
project(Strings C)

set(CMAKE_C_STANDARD 99)

add_executable(Strings main.c newStrings.h newStrings.c)
