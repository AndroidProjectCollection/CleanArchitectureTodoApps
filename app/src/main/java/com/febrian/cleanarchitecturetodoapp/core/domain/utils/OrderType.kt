package com.febrian.cleanarchitecturetodoapp.core.domain.utils

sealed class OrderType{
    object Ascending : OrderType()
    object Descending : OrderType()
}
