package com.bootstrap;

import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author bblonski
 */
@Named("myRealm")
public class DBRealm extends AuthorizingRealm {
    @Inject
    private Logger log;
    @Inject
    private UserPersistence userPersistence;

    @Inject
    public DBRealm() {
        setName("DBRealm");
        CredentialsMatcher authenticator = new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
                SaltedAuthenticationInfo saltedAuthenticationInfo = (SaltedAuthenticationInfo) authenticationInfo;
                UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
                String password = String.valueOf(token.getPassword());
                String hashedPasswordBase64 = new Sha256Hash(password, saltedAuthenticationInfo.getCredentialsSalt(), 1024).toBase64();
                return hashedPasswordBase64.equals(saltedAuthenticationInfo.getCredentials());
            }
        };
        setCredentialsMatcher(authenticator);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = (Long) principalCollection.fromRealm(getName()).iterator().next();
        User user = userPersistence.findOne(userId);
        if (user == null)
            return null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:view:" + userId);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userPersistence.findByEmail(token.getUsername());
        String password = String.valueOf(token.getPassword());
        String hashedPasswordBase64 = new Sha256Hash(password, user.getSalt(), 1024).toBase64();
        if (user != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), hashedPasswordBase64, getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
            return info;
        }
        return null;
    }
}
