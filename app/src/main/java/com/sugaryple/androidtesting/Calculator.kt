package com.sugaryple.androidtesting

class Calculator {
    fun evaluate(expresstion: String) : Int {
        var sum = 0
        expresstion.split("+").forEach {
            sum += it.toInt()
        }
        return sum
    }
}