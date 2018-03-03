package com.lubandj.master.been;

import com.google.gson.annotations.SerializedName;
import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/3/3.
 */

public class GetAddressBeen extends BaseEntity {

    /**
     * info : {"5":{"id":11,"linkman":"赵树振","phone":"13522374928","province":"青岛市","city":"青岛市","area":"南山区","address":"花园小区","housing_estate":"花园小区","house_number":"122"}}
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
         * 5 : {"id":11,"linkman":"赵树振","phone":"13522374928","province":"青岛市","city":"青岛市","area":"南山区","address":"花园小区","housing_estate":"花园小区","house_number":"122"}
         */

        @SerializedName("5")
        private _$5Bean _$5;

        public _$5Bean get_$5() {
            return _$5;
        }

        public void set_$5(_$5Bean _$5) {
            this._$5 = _$5;
        }

        public static class _$5Bean {
            /**
             * id : 11
             * linkman : 赵树振
             * phone : 13522374928
             * province : 青岛市
             * city : 青岛市
             * area : 南山区
             * address : 花园小区
             * housing_estate : 花园小区
             * house_number : 122
             */

            private int id;
            private String linkman;
            private String phone;
            private String province;
            private String city;
            private String area;
            private String address;
            private String housing_estate;
            private String house_number;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLinkman() {
                return linkman;
            }

            public void setLinkman(String linkman) {
                this.linkman = linkman;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHousing_estate() {
                return housing_estate;
            }

            public void setHousing_estate(String housing_estate) {
                this.housing_estate = housing_estate;
            }

            public String getHouse_number() {
                return house_number;
            }

            public void setHouse_number(String house_number) {
                this.house_number = house_number;
            }
        }
    }
}
