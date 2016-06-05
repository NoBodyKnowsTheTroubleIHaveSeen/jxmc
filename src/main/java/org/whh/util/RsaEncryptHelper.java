package org.whh.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

/**
 * RSA非对称加密辅助类
 * 
 * @author acer
 *
 */
public class RsaEncryptHelper
{

	public KeyPair getKeyPair(String args)
	{
		try
		{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 私钥
			// RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 公钥
			// RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			return keyPair;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * byte数组转为string
	 *
	 * @param encrytpByte
	 * @return
	 */
	private String bytesToString(byte[] encrytpByte)
	{
		String result = "";
		for (Byte bytes : encrytpByte)
		{
			result += (char) bytes.intValue();
		}
		return result;
	}

	/**
	 * 加密方法
	 *
	 * @param publicKey
	 * @param obj
	 * @return
	 */
	protected String encrypt(RSAPublicKey publicKey, byte[] obj)
	{
		if (publicKey != null)
		{
			try
			{
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] val = cipher.doFinal(obj);
				return bytesToString(val);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 解密方法
	 *
	 * @param privateKey
	 * @param obj
	 * @return
	 */
	protected String decrypt(RSAPrivateKey privateKey, byte[] obj)
	{
		if (privateKey != null)
		{
			try
			{
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] val = cipher.doFinal(obj);
				return bytesToString(val);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
