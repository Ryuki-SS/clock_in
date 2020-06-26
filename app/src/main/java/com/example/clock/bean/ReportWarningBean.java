package com.example.clock.bean;

import java.util.List;

public class ReportWarningBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","items":[{"id":"2017213072","name":"刘连波","age":"18","phone":"1322222222","address":"重庆","temperature":"36.5"}]}
     * msg : V2.5.1 YesApi App.SuperTable.GetMoreDataByMoreField
     */

    private int ret;
    private DataBean data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * err_code : 0
         * err_msg :
         * items : [{"id":"2017213072","name":"刘连波","age":"18","phone":"1322222222","address":"重庆","temperature":"36.5"}]
         */

        private int err_code;
        private String err_msg;
        private List<ItemsBean> items;

        public int getErr_code() {
            return err_code;
        }

        public void setErr_code(int err_code) {
            this.err_code = err_code;
        }

        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 2017213072
             * name : 刘连波
             * age : 18
             * phone : 1322222222
             * address : 重庆
             * temperature : 36.5
             */

            private String id;
            private String name;
            private String age;
            private String phone;
            private String address;
            private String temperature;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
