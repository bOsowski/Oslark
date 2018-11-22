package com.bosowski.oslark.utils

import java.util.Random

object Util {

  fun randomInt(random: Random, minInclusive: Int, maxInclusive: Int): Int {
    return random.nextInt(maxInclusive - minInclusive + 1) + minInclusive
  }
}
