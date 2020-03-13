package security;

import org.apache.directory.api.ldap.model.exception.LdapException;

public interface ILdapConnectionFactory {
    
    PooledLdapConnection getPooledConnection() throws LdapException;
    
}
