package com.example.forum

import java.security.MessageDigest

class Utils {
  companion object {
    /**
     * Генерирует хеш-строку.
     * @param input исходная строка.
     * @return хеш-строка.
     */
    fun hash(input: String): String {
      val bytes = input.toByteArray()
      val md = MessageDigest.getInstance("SHA-256")
      val digest = md.digest(bytes)
      return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
  }
}