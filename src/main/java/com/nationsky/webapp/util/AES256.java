package com.nationsky.webapp.util;
import java.io.UnsupportedEncodingException;
import java.security.Key;  
import java.security.Security;
 
import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.SecretKeySpec;  

import sun.misc.BASE64Decoder;
 
public class AES256{  
     
         /** 
         * 密钥算法 
         * java6支持56位密钥，bouncycastle支持64位 
         * */ 
        public static final String KEY_ALGORITHM="AES";  
           
        /** 
         * 加密/解密算法/工作模式/填充方式 
         *  
         * JAVA6 支持PKCS5PADDING填充方式 
         * Bouncy castle支持PKCS7Padding填充方式 
         * */ 
        public static final String CIPHER_ALGORITHM="AES/ECB/PKCS7Padding";  
           
        /** 
         *  
         * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥 
         * @return byte[] 二进制密钥 
         * */ 
        public static byte[] initkey() throws Exception{  
               
//          //实例化密钥生成器  
//          Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//          KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM, "BC");  
//          //初始化密钥生成器，AES要求密钥长度为128位、192位、256位  
////            kg.init(256);  
//          kg.init(128); 
//          //生成密钥  
//          SecretKey secretKey=kg.generateKey();  
//          //获取二进制密钥编码形式  
//          return secretKey.getEncoded();  
            //为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
            return new byte[] { 0x08, 0x08, 0x04, 0x0b, 0x02,
                    0x0f, 0x0b, 0x0c, 0x01, 0x03,
                    0x09, 0x07, 0x0c, 0x03, 0x07,
                    0x0a, 0x04, 0x0f, 0x06, 0x0f,
                    0x0e, 0x09, 0x05, 0x01, 0x0a,
                    0x0a, 0x01, 0x09, 0x06, 0x07,
                    0x09, 0x0d };
        }
 
        /** 
         * 转换密钥 
         * @param key 二进制密钥 
         * @return Key 密钥 
         * */ 
        public static Key toKey(byte[] key) throws Exception{  
            //实例化DES密钥  
            //生成密钥  
            SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);  
            return secretKey;  
        }  
           
        /** 
         * 加密数据 
         * @param data 待加密数据 
         * @param key 密钥 
         * @return byte[] 加密后的数据 
         * */ 
        public static byte[] encrypt(byte[] data,byte[] key) throws Exception{  
            //还原密钥  
            Key k=toKey(key);  
            /** 
             * 实例化 
             * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现 
             * Cipher.getInstance(CIPHER_ALGORITHM,"BC") 
             */ 
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");  
            //初始化，设置为加密模式  
            cipher.init(Cipher.ENCRYPT_MODE, k);  
            //执行操作  
            return cipher.doFinal(data);  
        } 
        /** 
         * 解密数据 
         * @param data 待解密数据 
         * @param key 密钥 
         * @return byte[] 解密后的数据 
         * */ 
        public static byte[] decrypt(byte[] data,byte[] key) throws Exception{  
            //欢迎密钥  
            Key k =toKey(key);  
            /** 
             * 实例化 
             * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现 
             * Cipher.getInstance(CIPHER_ALGORITHM,"BC") 
             */ 
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM, "BC");  
            //初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);  
            //执行操作  
            return cipher.doFinal(data);  
        }  
        
        public static byte[] hexStr2ByteArray(String hexString) throws UnsupportedEncodingException {  
            hexString = hexString.toLowerCase();  
            final byte[] byteArray = new byte[hexString.length() / 2];  
            int k = 0;  
            for (int i = 0; i < byteArray.length; i++) {  
                            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先  
                            //将hex 转换成byte   "&" 操作为了防止负数的自动扩展  
                            // hex转换成byte 其实只占用了4位，然后把高位进行右移四位  
                            // 然后“|”操作  低四位 就能得到 两个 16进制数转换成一个byte.  
                            //  
                byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);  
                byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);  
                byteArray[i] = (byte) (high << 4 | low);  
                k += 2;  
            }  
            return byteArray;  
        }  
        
        /** 
         * @param args 
         * @throws UnsupportedEncodingException 
         * @throws Exception  
         */ 
        public static void main(String[] args) throws UnsupportedEncodingException{  
        	
        	BASE64Decoder k = new BASE64Decoder();
        	
//        	String str = "123456";
//            //初始化密钥  
            byte[] key = null;
            try {
                key = AES256.initkey();
//                System.out.print("密钥：");  
//                for(int i = 0;i<key.length;i++){
//                    System.out.printf("%x", key[i]);
//                }
//                System.out.print("\n");
//                
//                byte[] y = str.getBytes();
//                System.out.print("加密前："); 
//                for(int i = 0;i<y.length;i++){
//                    System.out.printf("%x", y[i]);
//                }
//                System.out.print("\n");
                
                
                //加密数据  
//                byte[] data=AES256.encrypt(str.getBytes(), key); 
              
                //接收客户端传过来的base64加密后的密码解密
               byte[] data =  k.decodeBuffer("bLyuyKq8Pk38JVBex+biug==");
                
                //解密数据  
                data=AES256.decrypt(data, key);  
                System.out.println("解密后："+new String(data,"UTF-8")); 
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        	
        }  
    }