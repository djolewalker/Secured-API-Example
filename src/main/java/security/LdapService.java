/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.util.Hashtable;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import model.User;
import properties.Properties;

/**
 *
 * @author dimitrije
 */
public class LdapService {

    @Inject
    public Properties properties;

    private Hashtable env = new Hashtable();
//    private DirContext ctx;
//    private SearchControls searchControl;

    public LdapService() {
    }
    
    public void login(User user) throws Exception{
        
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");  
        env.put(Context.PROVIDER_URL, properties.getProperty("ldap-url"));
        env.put(Context.SECURITY_PRINCIPAL, ("cn="+user.getUsername()+','+properties.getProperty("ldap-sarch-base")).toString());
        env.put(Context.SECURITY_CREDENTIALS, user.getPassword());
        
        try {
            DirContext authCon = new InitialDirContext(env);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    
    

}
