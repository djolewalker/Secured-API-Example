package security;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionPool;

public class PooledLdapConnection implements AutoCloseable {

    private LdapConnection connection = null;
    private final LdapConnectionPool pool;

    public PooledLdapConnection(LdapConnectionPool pool) {
        this.pool = pool;
    }

    public LdapConnection getConnection() throws LdapException {
        if (this.connection != null) {
            return this.connection;
        }

        try {
            this.connection = this.pool.getConnection();
        } catch (Exception ex) {
            throw new LdapException(ex);
        }

        return this.connection;
    }

    @Override
    public void close() {
        if (this.connection != null) {
            try {
                this.pool.releaseConnection(this.connection);
            } catch (Exception ex) {
            }
        } else {
        }
    }
}
