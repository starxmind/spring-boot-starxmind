package com.starxmind.boot.starter.test;

import com.starxmind.boot.starter.test.entities.PersonEntity;
import org.junit.Test;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class EntityTest {
    @Test
    public void test() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCreatedInfo(123);
        personEntity.setName("pizzalord");
        System.out.println(personEntity);
    }
}
