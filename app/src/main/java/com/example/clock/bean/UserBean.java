package com.example.clock.bean;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class UserBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","items":[{"name":"肖旭辛"}]}
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
         * items : [{"name":"肖旭辛"}]
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

        public static class ItemsBean extends LitePalSupport{
            /**
             * name : 肖旭辛
             * id : 2017213071
             */

            private int id;

            public int getId() { return id; }

            public void setId(int id) {
                this.id = id;
            }

            private String userId;


            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    }
}

