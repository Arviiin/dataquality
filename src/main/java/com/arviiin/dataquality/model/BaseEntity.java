package com.arviiin.dataquality.model;

import java.util.Date;

public class BaseEntity {
    /*阿里开发手册
    【强制】表必备三字段： id ,  gmt _ create ,  gmt _ modified 。
    说明：其中 id 必为主键，类型为 bigint unsigned 、单表时自增、步长为 1。 gmt_create,
    gmt_modified 的类型均为 datetime 类型，前者现在时表示主动创建，后者过去分词表示被
    动更新。*/
    /*@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid", strategy = "uuid")*/
    private String id;

    //@Column(name= "create_date") //创建时间
    private Date createDate = new Date();

    //@Column(name= "update_date")//修改时间
    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
