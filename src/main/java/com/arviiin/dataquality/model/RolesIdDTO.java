package com.arviiin.dataquality.model;

import java.util.List;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/4/11
 * @Version 1.0.0
 */

public class RolesIdDTO {
    private Long id;
    private List<Long> rids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getRids() {
        return rids;
    }

    public void setRids(List<Long> rids) {
        this.rids = rids;
    }
}
