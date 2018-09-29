package com.sugaryple.androidtesting

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.mockito.Mockito.*
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

    @Test
    fun mockを使ってLinkedListを検証して見ました() {
        //Mockオブジェクト生成
        //mockを使うkotlinではgenericsするのが難しくてライブラリを使いました。(http://bit.ly/2xYXqkN)
        val mockedList : LinkedList<String> = mock()

        //メソッドを呼び出す(ただ、返還値や動作を設定しなかったので何の事も起きてない)
        mockedList.add("one")
        mockedList.clear()

        //addとclearメソッドが１回づつ呼び出したことを検証
        verify(mockedList, times(1)).add("one")
        verify(mockedList, times(1)).clear()
    }
}