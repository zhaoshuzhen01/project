package com.lubandj.master.been;

import com.example.baselibrary.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/4/29.
 */

public class PingJiaBeen extends BaseEntity {

    /**
     * info : {"percent":"90%","count":1232,"result":[{"name":"账上","face":"http://xxx/asds.jpg","content":"adasdasd","img":["http://asdasd/x.jpg,http://asd/x.jpg","http://asdasd/x.jpg,http://asd/x.jpg"],"create_time":"2019-10-1 12:12:22","star":"5"}]}
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
         * percent : 90%
         * count : 1232
         * result : [{"name":"账上","face":"http://xxx/asds.jpg","content":"adasdasd","img":["http://asdasd/x.jpg,http://asd/x.jpg","http://asdasd/x.jpg,http://asd/x.jpg"],"create_time":"2019-10-1 12:12:22","star":"5"}]
         */

        private String percent;
        private int count;
        private List<ResultBean> result;

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * name : 账上
             * face : http://xxx/asds.jpg
             * content : adasdasd
             * img : ["http://asdasd/x.jpg,http://asd/x.jpg","http://asdasd/x.jpg,http://asd/x.jpg"]
             * create_time : 2019-10-1 12:12:22
             * star : 5
             */

            private String nickname;
            private String face_url;
            private String content;
            private String create_time;
            private String star;
            private List<String> img;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFace_url() {
                return face_url;
            }

            public void setFace_url(String face_url) {
                this.face_url = face_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            public List<String> getImg() {
                return img;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }
        }
    }
}
