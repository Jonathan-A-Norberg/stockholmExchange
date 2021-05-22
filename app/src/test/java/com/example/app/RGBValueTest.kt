package com.example.app

import com.example.network.utils.RGBValue
import com.example.network.utils.calcRGBDistance
import junit.framework.Assert.assertEquals
import org.junit.Test

class RGBValueTest {
    @Test
    fun oneItemNotEmptyPrice() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
        )

        listOf.forEach { rgbValue ->
            val filter = listOf.filterNot { rgbValue == it }
            assert(calcRGBDistance(rgbValue, filter) >= 0)
            assertEquals(0.0, calcRGBDistance(rgbValue, filter))

        }

    }

    @Test
    fun threeItemsNotEmptyPrice() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
            RGBValue(255, 0, 0),
            RGBValue(0, 0, 255),
        )
        testNotEmptyCalculation(listOf)
    }

    @Test
    fun multipleItemsNotEmptyPrice() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
            RGBValue(255, 0, 0),
            RGBValue(0, 0, 255),
            RGBValue(255, 255, 255),
            RGBValue(54, 255, 123),
            RGBValue(0, 123, 0),
            RGBValue(21, 255, 0),
            RGBValue(0, 255, 0),
            RGBValue(0, 122, 0),
            RGBValue(12, 123, 213),
            RGBValue(12, 123, 0),
        )

        testNotEmptyCalculation(listOf)

    }


    @Test
    fun checkDistanceToNeighbour() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
            RGBValue(0, 254, 0),
        )
        listOf.forEach { rgbValue ->
            val filter = listOf.filterNot { rgbValue == it }
            assertEquals(1.0, calcRGBDistance(rgbValue, filter))

        }
    }

    @Test
    fun checkDistanceToNeighbour_2() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
            RGBValue(1, 254, 0),
        )
        listOf.forEach { rgbValue ->
            val filter = listOf.filterNot { rgbValue == it }
            assertEquals(1.4142135623730951, calcRGBDistance(rgbValue, filter))

        }
    }

    @Test
    fun checkDistanceToNeighbour_3() {
        val listOf = listOf(
            RGBValue(0, 255, 0),
            RGBValue(1, 254, 1),
        )
        listOf.forEach { rgbValue ->
            val filter = listOf.filterNot { rgbValue == it }
            assertEquals(1.7320508075688772, calcRGBDistance(rgbValue, filter))

        }
    }

    private fun testNotEmptyCalculation(listOf: List<RGBValue>) {
        listOf.forEach { rgbValue ->
            val filter = listOf.filterNot { rgbValue == it }
            assert(calcRGBDistance(rgbValue, filter) >= 0)

        }
    }
}