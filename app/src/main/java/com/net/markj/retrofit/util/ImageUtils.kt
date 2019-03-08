package com.net.markj.retrofit.util

/**
 * Created by Kron Xu on 2018/7/2
 */
class ImageUtils {
    companion object {
        fun getScaleImageSize(imageSize: Pair<Int, Int>, screenSize: Pair<Float, Float>): Pair<Int, Int> {
            val (w, h) = imageSize
            val (screenWidth, screenHeight) = screenSize

            val widthScale = (screenWidth) / w
            val heightScale = (screenHeight) / h

            val overrideW = (widthScale * w).toInt()
            val overrideH = (heightScale * h).toInt()
            return Pair(overrideW, overrideH)
        }
    }
}