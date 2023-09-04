package com.starxmind.boot.ldap;

import com.starxmind.bass.sugar.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Ldap操作模板
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public class LdapClient {
    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * 获取用户信息
     *
     * @return
     */
    public LdapUser queryUserByUid(String uid) {
        //过滤条件
//        String filter = "(&(objectClass=*)(uid=*))";
//        String filter = "(|(uid=" + uid + "))";
        String filter = "(uid=" + uid + ")";
        List<LdapUser> ldapUsers = searchUsersByFilter(filter);
        if (CollectionUtils.isEmpty(ldapUsers)) {
            return null;
        }
        return ldapUsers.get(0);
    }

    public List<LdapUser> queryAllUsers() {
        String filter = "(&(objectClass=*)(uid=*))";
        return searchUsersByFilter(filter);
    }

    public List<LdapUser> searchUsersByFilter(String filter) {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        List<LdapUser> search = ldapTemplate.search("", filter, controls, (ContextMapper<LdapUser>) o -> {
            DirContextOperations ctx = (DirContextOperations) o;

            byte[] userPassword = (byte[]) ctx.getObjectAttribute("userPassword");
            String userPasswordDecoded = new String(userPassword);

            return LdapUser.builder()
                    .cn(ctx.getStringAttribute("cn"))
                    .sn(ctx.getStringAttribute("sn"))
                    .uid(ctx.getStringAttribute("uid"))
                    .userPassword(userPasswordDecoded)
                    .displayName(ctx.getStringAttribute("displayName"))
                    .mail(ctx.getStringAttribute("mail"))
                    .description(ctx.getStringAttribute("description"))
                    .uid(ctx.getStringAttribute("uid"))
                    .build();
        });

        return search;
    }

    public void authenticate(String uid, String password) throws NamingException, NoSuchAlgorithmException {
        LdapUser ldapUser = queryUserByUid(uid);
        Asserts.notNull(ldapUser, "无此用户");
        SHA1Utils.verifyPassword(ldapUser.getUserPassword(), password);
    }
}
