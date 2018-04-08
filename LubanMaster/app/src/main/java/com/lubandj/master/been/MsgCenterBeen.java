package com.lubandj.master.been;

import com.google.gson.annotations.SerializedName;
import com.lubandj.master.httpbean.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class MsgCenterBeen extends BaseEntity {

    /**
     * info : {"new":0,"list":[{"title":"系统消息","content":"test","type":"1","is_read":"1","datatime":"2017-12-23 17:22:07"}]}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * new : 0
         * list : [{"title":"系统消息","content":"test","type":"1","is_read":"1","datatime":"2017-12-23 17:22:07"}]
         */

        @SerializedName("new")
        private int newX;
        private List<ListBean> list;

        public int getNewX() {
            return newX;
        }

        public void setNewX(int newX) {
            this.newX = newX;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 系统消息
             * content : test
             * type : 1
             * is_read : 1
             * datatime : 2017-12-23 17:22:07
             */

            private String title;
            private String content;
            private String type;
            private String is_read;
            private String datatime;
            private String ticket_sn;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIs_read() {
                return is_read;
            }

            public void setIs_read(String is_read) {
                this.is_read = is_read;
            }

            public String getDatatime() {
                return datatime;
            }

            public void setDatatime(String datatime) {
                this.datatime = datatime;
            }

            public String getTicket_sn() {
                return ticket_sn;
            }

            public void setTicket_sn(String ticket_sn) {
                this.ticket_sn = ticket_sn;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
