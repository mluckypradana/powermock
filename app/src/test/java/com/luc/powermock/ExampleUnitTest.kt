package com.luc.powermock

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(fullyQualifiedNames = ["com.luc.powermock.*"])
class ExampleUnitTest {
    private lateinit var collaborator: CollaboratorWithFinalMethods

    @Before
    fun setup() {
        val mock: CollaboratorWithFinalMethods = mock(CollaboratorWithFinalMethods::class.java)
        whenNew(CollaboratorWithFinalMethods::class.java).withNoArguments().thenReturn(mock)
        collaborator = CollaboratorWithFinalMethods()
    }

    @Test
    fun mockFinalClass() {
        verifyNew(CollaboratorWithFinalMethods::class.java).withNoArguments()
    }

    @Test
    fun mockFinalMethod() {
        `when`(collaborator.helloMethod()).thenReturn("Hello Baeldung!")
        val welcome = collaborator.helloMethod()
        Mockito.verify(collaborator).helloMethod()
        assert("Hello Baeldung!" == welcome)
    }

    @Test
    fun mockStaticMethods() {
        mockStatic(CollaboratorWithStaticMethods.Companion::class.java)
        `when`(CollaboratorWithStaticMethods.firstMethod(anyString())).thenReturn("Hello Baeldung!")
        `when`(CollaboratorWithStaticMethods.secondMethod()).thenReturn("Nothing special")
        CollaboratorWithStaticMethods.thirdMethod()

        val firstWelcome = CollaboratorWithStaticMethods.firstMethod("Whoever")
        val secondWelcome = CollaboratorWithStaticMethods.firstMethod("Whatever")

        assert("Hello Baeldung!" == firstWelcome)
        assert("Hello Baeldung!" == secondWelcome)

        verifyStatic(CollaboratorWithStaticMethods::class.java, Mockito.times(2))
        CollaboratorWithStaticMethods.firstMethod(Mockito.anyString())

        verifyStatic(CollaboratorWithStaticMethods::class.java, Mockito.never())
        CollaboratorWithStaticMethods.secondMethod()
    }

    @Test
    fun givenStaticMethodsWhenUsingPowermock() {
        doThrow(RuntimeException()).`when`(CollaboratorWithStaticMethods::class.java)
        CollaboratorWithStaticMethods.thirdMethod()
    }

    @Test
    fun mockPartial() {
        spy(CollaboratorForPartialMocking::class.java)
        `when`(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method.")
        val returnValue = CollaboratorForPartialMocking.staticMethod()
        verifyStatic(CollaboratorWithStaticMethods::class.java)
        CollaboratorForPartialMocking.staticMethod()
        assert("I am a static mock method." == returnValue)
    }

    @Test
    fun mockPrivateMethod() {
        val mock = mock(CollaboratorForPartialMocking::class.java)
        `when`<String>(mock, "privateMethod").thenReturn("I am a private mock method.")
        val returnValue = mock.privateMethodCaller()
        verifyPrivate(mock).invoke("privateMethod")
        assert("I am a private mock method. Welcome to the Java world." == returnValue)
    }
}
