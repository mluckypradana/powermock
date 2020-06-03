package com.luc.powermock

class CollaboratorForPartialMocking {

    private fun privateMethod(): String {
        return "Hello Baeldung!"
    }

    fun privateMethodCaller(): String {
        return privateMethod() + " Welcome to the Java world."
    }

    companion object {
        fun staticMethod(): String {
            return "Hello Baeldung!"
        }
    }
}