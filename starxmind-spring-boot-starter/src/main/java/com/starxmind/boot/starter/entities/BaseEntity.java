package com.starxmind.boot.starter.entities;

import lombok.Data;

import java.util.Date;

/**
 * 基础对象
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public abstract class BaseEntity {
    protected Date createdAt;
    protected long createdBy;
    protected Date updatedAt;
    protected long updatedBy;

    /**
     * 设置新增信息
     *
     * @param userId 用户ID
     */
    public void setCreatedInfo(long userId) {
        Date now = new Date();

        this.createdAt = now;
        this.updatedAt = now;
        this.createdBy = userId;
        this.updatedBy = userId;
    }

    /**
     * 设置新增信息
     *
     * @param userId 用户ID
     */
    public void setUpdatedInfo(long userId) {
        Date now = new Date();

        this.updatedAt = now;
        this.updatedBy = userId;
    }
}
