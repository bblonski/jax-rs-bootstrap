package com.bootstrap;

import com.bootstrap.models.User;
import com.bootstrap.persistence.UserPersistence;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author bblonski
 */
@Named
public class DBRealm extends AuthorizingRealm {
    private static Logger log = LoggerFactory.getLogger(DBRealm.class);

    @Inject
    private UserPersistence userPersistence;

    @Inject
    public DBRealm() {
        PersistenceConfig config = new PersistenceConfig();
        userPersistence = config.createUserPersistence(config.getRepositoryFactory(config.getEm()));
        setName("DBRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Long userId = (Long) principalCollection.fromRealm(getName()).iterator().next();
        User user = userPersistence.findOne(userId);
        if (user == null)
            return null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("view:user");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userPersistence.findByEmail(token.getUsername());
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        }
        return null;
    }
}
