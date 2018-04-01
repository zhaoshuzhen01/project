package com.example.baselibrary;

import java.io.Serializable;

/**
 * @author: lj
 * @Time: 2017/12/18 11:17
 * @Description: This is BaseEntity
 */

public class BaseEntity implements Serializable{


    /**
     * code : 0
     * message : 发送成功
     * info :
     */

    private int code;
    private String message;
//    private String info;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", info='"  + '\'' +
                '}';
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   /* public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }*/
}
