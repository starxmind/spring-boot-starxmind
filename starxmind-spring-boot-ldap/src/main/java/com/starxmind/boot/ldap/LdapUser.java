package com.starxmind.boot.ldap;

import lombok.*;

/**
 * LDAP用户
 *
 * @author pizzalord
 * @since 1.0
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class LdapUser {
    public String cn;
    public String sn;
    public String uid;
    public String userPassword;
    public String displayName;
    public String mail;
    public String description;
    public String uidNumber;
    public String gidNumber;
}
