package com.cdi.shoppingMall.net;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by jiao.zhu on 2017/2/14.
 * share村对象时的转换器
 */

public class ShareObjParser {
    /**
     * 将复杂数据(obj)转换为字节码 and
     * 将字节码按Base64编码格式转换为String
     *
     * @param obj 将传入的对象转换成字节码String
     */
    public static String convertObjToBase64ClazzStr(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            String bytesToHexString = new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
            if (!TextUtils.isEmpty(bytesToHexString)) {
                return bytesToHexString;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object convertBase64ClazzStrToObj(String base64ClazzStr) {
        if (!TextUtils.isEmpty(base64ClazzStr)) {
            byte[] stringToBytes = Base64.decode(base64ClazzStr, Base64.DEFAULT);
            ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
            ObjectInputStream is = null;
            try {
                is = new ObjectInputStream(bis);
                Object obj = is.readObject();
                if (null != obj) {
                    return obj;
                }
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
