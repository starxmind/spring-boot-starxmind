package com.starxmind.boot.starter.test.entities;

import com.starxmind.boot.starter.entities.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@ToString(callSuper = true)
@Data
public class PersonEntity extends BaseEntity {
    private String name;
}
