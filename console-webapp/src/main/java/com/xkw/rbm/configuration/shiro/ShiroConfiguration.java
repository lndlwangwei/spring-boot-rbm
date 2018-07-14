package com.xkw.rbm.configuration.shiro;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {

//    @Bean
//    public Realm realm() {
//        return new AuthorizingRealm() {
//            @Override
//            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//                return null;
//            }
//
//            @Override
//            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//                return null;
//            }
//        };
//    }

//    @Autowired
//    private SecurityManager securityManager;
//
//    @PostConstruct
//    private void initStaticSecurityManager() {
//        SecurityUtils.setSecurityManager(securityManager);
//    }

//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//
//        // logged in users with the 'admin' role
//        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
//
//        // logged in users with the 'document:read' permission
//        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
//
//        // all other paths require a logged in user
//        chainDefinition.addPathDefinition("/**", "authc");
//        return chainDefinition;
//    }
//
//    @Bean
//    protected CacheManager cacheManager() {
//        return new MemoryConstrainedCacheManager();
//    }
}
