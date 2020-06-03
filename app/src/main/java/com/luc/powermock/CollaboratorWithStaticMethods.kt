package com.luc.powermock

class CollaboratorWithStaticMethods {
    companion object {
        fun firstMethod(name: String): String? {
            return "Hello $name !"
        }

        fun secondMethod(): String? {
            return "Hello no one!"
        }

        fun thirdMethod(): String? {
            return "Hello no one again!"
        }
    }
}