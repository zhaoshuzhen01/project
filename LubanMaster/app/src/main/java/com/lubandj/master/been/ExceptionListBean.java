package com.lubandj.master.been;

import java.util.List;

/**
 * Created by lj on 2017/12/23.
 */

public class ExceptionListBean {

    /**
     * code : 0
     * msg : 成功
     * info : [{"problemId":1,"text":"用户主动取消订单"},{"problemId":2,"text":"已到达目的地，与用户无法取得联系"},{"problemId":3,"text":"临时有事，请求更换师傅"},{"problemId":4,"text":"其他"}]
     */

    private int code;
    private String msg;
    private List<InfoBean> info;

    @Override
    public String toString() {
        return "ExceptionListBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * problemId : 1
         * text : 用户主动取消订单
         */

        private int problemId;
        private String text;

        @Override
        public String toString() {
            return "InfoBean{" +
                    "problemId=" + problemId +
                    ", text='" + text + '\'' +
                    '}';
        }

        public int getProblemId() {
            return problemId;
        }

        public void setProblemId(int problemId) {
            this.problemId = problemId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
