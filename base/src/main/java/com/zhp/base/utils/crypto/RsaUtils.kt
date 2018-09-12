@file:Suppress("unused")

package com.zhp.base.utils.crypto

import android.util.Base64
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException


private val ALGORITHM = "RSA"

/**
 * 从字符串中加载公钥
 *
 * @param publicKeyStr 公钥数据字符串
 * @return
 * @throws Exception 加载公钥时产生的异常
 */
@Throws(Exception::class)
fun loadPublicKeyByStr(publicKeyStr: String): RSAPublicKey {
    try {
        val buffer = Base64.decode(publicKeyStr, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(buffer)
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    } catch (e: NoSuchAlgorithmException) {
        throw Exception("无此算法")
    } catch (e: InvalidKeySpecException) {
        throw Exception("公钥非法")
    } catch (e: NullPointerException) {
        throw Exception("公钥数据为空")
    }

}

/**
 * 从字符串中加载私钥
 *
 * @param privateKeyStr
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun loadPrivateKeyByStr(privateKeyStr: String): RSAPrivateKey {
    try {
        val buffer = Base64.decode(privateKeyStr, Base64.DEFAULT)
        val keySpec = PKCS8EncodedKeySpec(buffer)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    } catch (e: NoSuchAlgorithmException) {
        throw Exception("无此算法")
    } catch (e: InvalidKeySpecException) {
        throw Exception("私钥非法")
    } catch (e: NullPointerException) {
        throw Exception("私钥数据为空")
    }

}

/**
 * 公钥加密过程
 *
 * @param publicKey     公钥
 * @param plainTextData 明文数据
 * @return
 * @throws Exception 加密过程中的异常信息
 */
@Throws(Exception::class)
fun encrypt(publicKey: RSAPublicKey?, plainTextData: ByteArray): ByteArray? {
    if (publicKey == null) {
        throw Exception("加密公钥为空, 请设置")
    }
    var cipher: Cipher? = null
    try {
        // 必须使用RSA/ECB/PKCS1Padding而不是RSA，否则每次加密结果一样
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher!!.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(plainTextData)
    } catch (e: NoSuchAlgorithmException) {
        throw Exception("无此加密算法")
    } catch (e: NoSuchPaddingException) {
        e.printStackTrace()
        return null
    } catch (e: InvalidKeyException) {
        throw Exception("加密公钥非法,请检查")
    } catch (e: IllegalBlockSizeException) {
        throw Exception("明文长度非法")
    } catch (e: BadPaddingException) {
        throw Exception("明文数据已损坏")
    }

}

/**
 * 私钥解密过程
 *
 * @param privateKey 私钥
 * @param cipherData 密文数据
 * @return 明文
 * @throws Exception 解密过程中的异常信息
 */
@Throws(Exception::class)
fun decrypt(privateKey: RSAPrivateKey?, cipherData: ByteArray): ByteArray? {
    if (privateKey == null) {
        throw Exception("解密私钥为空,请设置")
    }
    var cipher: Cipher? = null
    try {
        // 必须使用RSA/ECB/PKCS1Padding而不是RSA，否则解密会乱码
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher!!.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(cipherData)
    } catch (e: NoSuchAlgorithmException) {
        throw Exception("无此解密算法")
    } catch (e: NoSuchPaddingException) {
        e.printStackTrace()
        return null
    } catch (e: InvalidKeyException) {
        throw Exception("解密私钥非法,请检查")
    } catch (e: IllegalBlockSizeException) {
        throw Exception("密文长度非法")
    } catch (e: BadPaddingException) {
        throw Exception("密文数据已损坏")
    }

}