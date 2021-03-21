package com.bjowernode.CRM.settings.domain;

public class User {
    /*
     *关于登陆操作
     *  验证登陆和密码
     *  User user=执行语句select * from tbl_user where LoginAct=? and LoginPwd=?
     *  if user==null,证明账号密码错误
     *  if user!==null,证明账号密码正确，需要继续向下验证其他字段信息
     *  从user中get到其他信息
     *  expireTime验证失效时间，临时用户，到期自动超时销毁
     *  lockState锁定状态，if==0锁定，if==1正常登录
     *  allowIps，IP地址，可以针对IP拦截
     */
    //对应表中的User,每一个变量都对应一个表中字段
    private String id;  //编号，主键
    private String loginAct;  //登录账号
    private String name;  //用户真实姓名
    private String loginPwd;  //登录密码
    private String email;  //用户邮箱
    private String expireTime;  //失效时间  19位字符串（yyyy-MM-dd HH-mm-ss）
    private String lockState;  //锁定状态 0锁定，1启用
    private String deptno;  //部门编号
    private String allowIps;  //允许访问的IP地址，可以针对IP拦截
    private String createTime;  //记录创建时间
    private String createBy;  //创建人
    private String editTime;  //修改时间 与失效时间格式保持一致
    private String editBy;  //修改人

    //请注意，这里的位置就不允许出现带参数的构造方法，一旦出现会很难维护这种大量参数的方法

    //setter and getter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
