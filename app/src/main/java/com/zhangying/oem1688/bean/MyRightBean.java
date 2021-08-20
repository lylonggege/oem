package com.zhangying.oem1688.bean;

public class MyRightBean {

    /**
     * done : true
     * msg :
     * retval : {"content":"https://m.oem1688.cn/?app=default&act=factoryin&wx=1","type":1,"showLoad":1}
     */

    private boolean done;
    private String msg;
    /**
     * content : https://m.oem1688.cn/?app=default&act=factoryin&wx=1
     * type : 1
     * showLoad : 1
     */

    private RetvalBean retval;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RetvalBean getRetval() {
        return retval;
    }

    public void setRetval(RetvalBean retval) {
        this.retval = retval;
    }

    public static class RetvalBean {
        private String content;
        private int type;
        private int showLoad;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShowLoad() {
            return showLoad;
        }

        public void setShowLoad(int showLoad) {
            this.showLoad = showLoad;
        }
    }
}
