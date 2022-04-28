package com.febrian.cleanarchitecturetodoapp.core.domain.utils

sealed class LayoutType{
    object List : LayoutType()
    object Grid : LayoutType()

}