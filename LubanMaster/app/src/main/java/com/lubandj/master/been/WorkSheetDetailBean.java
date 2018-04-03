package com.lubandj.master.been;

import java.util.List;

/**
 * Created by lj on 2017/12/22.
 */

public class WorkSheetDetailBean {


    /**
     * code : 0
     * message : 成功
     * info : {"id":"2","ticketSn":"LB20171221","beginTime":"2017-12-25 12:04:12","endTime":"2017-12-25 12:47:32","timeStr":"2017-12-25(周一) 12:04-12:47","status":"待执行","custName":"dennis","custPhone":"18618495512","remark":"你好n不好啊","address":"北京炸酱面的地方","orderTime":"2017-12-25 12:04-12:47","serviceItem":[{"item":"暖气服务-暖气水管","num":"1"},{"item":"暖气服务-暖气片","num":"2"}]}
     */

    private int code;
    private String message;
    private InfoBean info;

    @Override
    public String toString() {
        return "WorkSheetDetailBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", info=" + info +
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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 2
         * ticketSn : LB20171221
         * beginTime : 2017-12-25 12:04:12
         * endTime : 2017-12-25 12:47:32
         * timeStr : 2017-12-25(周一) 12:04-12:47
         * status : 待执行
         * custName : dennis
         * custPhone : 18618495512
         * remark : 你好n不好啊
         * address : 北京炸酱面的地方
         * orderTime : 2017-12-25 12:04-12:47
         * serviceItem : [{"item":"暖气服务-暖气水管","num":"1"},{"item":"暖气服务-暖气片","num":"2"}]
         */

        private String id;
        private String ticketSn;
        private String beginTime;
        private String endTime;
        private String timeStr;
        private String status;
        private String custName;
        private String custPhone;
        private String remark;
        private String address;
        private String orderTime;
        private List<ServiceItemBean> serviceItem;
        private String statusText;
        private String work_end_time;//完成时间
        private String work_begin_time;//上门时间
        private String close_time;//取消时间

        @Override
        public String toString() {
            return "InfoBean{" +
                    "id='" + id + '\'' +
                    ", ticketSn='" + ticketSn + '\'' +
                    ", beginTime='" + beginTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", timeStr='" + timeStr + '\'' +
                    ", status='" + status + '\'' +
                    ", custName='" + custName + '\'' +
                    ", custPhone='" + custPhone + '\'' +
                    ", remark='" + remark + '\'' +
                    ", address='" + address + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", serviceItem=" + serviceItem +
                    ", statusText='" + statusText + '\'' +
                    '}';
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }


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

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustPhone() {
            return custPhone;
        }

        public void setCustPhone(String custPhone) {
            this.custPhone = custPhone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public List<ServiceItemBean> getServiceItem() {
            return serviceItem;
        }

        public void setServiceItem(List<ServiceItemBean> serviceItem) {
            this.serviceItem = serviceItem;
        }

        public String getWork_end_time() {
            return work_end_time;
        }

        public void setWork_end_time(String work_end_time) {
            this.work_end_time = work_end_time;
        }

        public String getWork_begin_time() {
            return work_begin_time;
        }

        public void setWork_begin_time(String work_begin_time) {
            this.work_begin_time = work_begin_time;
        }

        public String getClose_time() {
            return close_time;
        }

        public void setClose_time(String close_time) {
            this.close_time = close_time;
        }

        public static class ServiceItemBean {

            private String item;
            private String num;
            private String status;
            private String statusText;

            @Override
            public String toString() {
                return "ServiceItemBean{" +
                        "item='" + item + '\'' +
                        ", num='" + num + '\'' +
                        ", status='" + status + '\'' +
                        ", statusText='" + statusText + '\'' +
                        '}';
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStatusText() {
                return statusText;
            }

            public void setStatusText(String statusText) {
                this.statusText = statusText;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
