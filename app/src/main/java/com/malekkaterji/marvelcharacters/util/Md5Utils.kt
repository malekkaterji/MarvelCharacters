package com.malekkaterji.marvelcharacters.util

import java.math.BigInteger
import java.security.MessageDigest

class Md5Utils {
    fun generateHash (content : String) : String {
        val md: MessageDigest = MessageDigest.getInstance("MD5")
        md.update(content.encodeToByteArray(),0,content.length)
        return BigInteger(1, md.digest()).toString(16)
    }
}