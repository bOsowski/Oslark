package com.bosowski.oslark.utils

import java.util.Random

object Util {

  fun map(value: Float, inputStart: Float, inputEnd: Float, outputStart: Float, outputEnd: Float): Float{
    val inputRange = inputEnd - inputStart
    val outputRange = outputEnd - outputStart
    return (value - inputStart) * outputRange / inputRange + outputStart
  }

  fun randomInt(random: Random, minInclusive: Int, maxInclusive: Int): Int {
    return random.nextInt(maxInclusive - minInclusive + 1) + minInclusive
  }

  fun randomFloat(random: Random, minInclusive: Float, maxInclusive: Float): Float{
    return (random.nextFloat() * ((maxInclusive - minInclusive))) + minInclusive
  }
}
