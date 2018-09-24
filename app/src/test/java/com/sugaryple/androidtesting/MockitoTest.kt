package com.sugaryple.androidtesting

import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import java.util.*

class MockitoTest {

    @Test
    fun basicMockitoTest() {
        //Mockオブジェクト生成
        val mockedList = mock(LinkedList::class.java)

        //mockedList[0]が返還する値を'first'で設定
        `when`(mockedList[0]).thenReturn("first")

        //first確認
        assertEquals(mockedList[0], "first")

        //[999]は返還値が指定しないのでnull
        assertNull(mockedList[999])
    }
}