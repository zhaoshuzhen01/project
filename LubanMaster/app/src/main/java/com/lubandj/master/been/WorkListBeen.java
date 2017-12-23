package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class WorkListBeen extends BaseEntity {

    /**
     * code : 0
     * message : 成功
     * info : [{"id":"6","ticketSn":"LB20171221","beginTime":"2017-12-25 12:04:12","endTime":"2017-12-25 12:47:32","timeStr":"2017-12-25(周一) 12:04-12:47","status":"异常","address":"北京炸酱面的地方"},{"id":"25","ticketSn":"LB20171221","beginTime":"2017-12-25 12:04:12","endTime":"2017-12-25 12:47:32","timeStr":"2017-12-25(周一) 12:04-12:47","status":"异常","address":"北京炸酱面的地方"},{"id":"31","ticketSn":"LB20171221","beginTime":"2017-12-25 12:04:12","endTime":"2017-12-25 12:47:32","timeStr":"2017-12-25(周一) 12:04-12:47","status":"异常","address":"北京炸酱面的地方"},{"id":"32","ticketSn":"LB20171221","beginTime":"2017-12-25 12:04:12","endTime":"2017-12-25 12:47:32","timeStr":"2017-12-25(周一) 12:04-12:47","status":"异常","address":"北京炸酱面的地方"}]
     * count : 1
     */
    private int count;
    private List<InfoBean> info;



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 6
         * ticketSn : LB20171221
         * beginTime : 2017-12-25 12:04:12
         * endTime : 2017-12-25 12:47:32
         * timeStr : 2017-12-25(周一) 12:04-12:47
         * status : 异常
         * address : 北京炸酱面的地方
         */

        private String id;
        private String ticketSn;
        private String beginTime;
        private String endTime;
        private String timeStr;
        private String status;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTicketSn() {
            return ticketSn;
        }

        public void setTicketSn(String ticketSn) {
            this.ticketSn = ticketSn;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
