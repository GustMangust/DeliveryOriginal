package com.Delivery_Project.utility

import java.math.BigInteger
import java.security.MessageDigest

class EncryptionUtility {
    companion object{
        fun encryptString(password: String): String{
            val md = MessageDigest.getInstance("SHA-512")
            val messageDigest = md.digest(password.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
                while (hashtext.length < 32) {
                    hashtext = "0$hashtext"
                }
            return hashtext
        }
    }
}