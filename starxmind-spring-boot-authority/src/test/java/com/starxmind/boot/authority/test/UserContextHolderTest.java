package com.starxmind.boot.authority.test;

import com.starxmind.boot.authority.user.AuthorizedUser;
import com.starxmind.boot.authority.user.UserContextHolder;
import org.junit.Test;

public class UserContextHolderTest {
    @Test
    public void testUserType() {
        AuthorizedUser user = UserContextHolder.get();

    }
}
