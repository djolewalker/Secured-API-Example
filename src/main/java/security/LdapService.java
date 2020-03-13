/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.IOException;
import javax.inject.Inject;
import model.User;
import org.apache.directory.api.ldap.model.exception.LdapException;
import properties.IJediProperties;

public class LdapService {

    @Inject
    public IJediProperties properties;

    @Inject
    private ILdapConnectionFactory ldapConnection;

    public LdapService() {
    }

    public void login(User user) throws IOException, LdapException {

        String usrDn = "uid=" + user.getUsername() + ',' + properties.getString("ldap.sarch.base", "");
        String pass = user.getPassword();

        try (PooledLdapConnection conn = this.ldapConnection.getPooledConnection()) {
            conn.getConnection().bind(usrDn, pass);
        }
    }

}
