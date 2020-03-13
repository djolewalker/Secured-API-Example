package security;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.ValidatingPoolableLdapConnectionFactory;
import properties.IJediProperties;
import properties.JediProperties;

@Singleton
public class LdapConnectionFactory implements ILdapConnectionFactory{

    @Inject
    public IJediProperties properties;

    private static final Pattern rxLDAPCONFIG = Pattern.compile("^(?<protocol>ldaps?)://(?<server>[a-zA-Z]{1}[a-zA-Z0-9\\-_\\.]+):(?<port>[0-9]+)$", Pattern.CASE_INSENSITIVE);

    private final String ldapServer;
    private final int ldapPort;
    private final boolean ldapSecure;

    private LdapConnectionPool pool;

    public LdapConnectionFactory() {
        JediProperties properties = new JediProperties();
        
        //seckanje rl na delove
        System.out.println(properties.getString("ldap.url", ""));
        Matcher m = rxLDAPCONFIG.matcher(properties.getString("ldap.url", ""));

        //server url
        m.matches();
        this.ldapServer = m.group("server");
        this.ldapPort = Integer.parseInt(m.group("port"));
        this.ldapSecure = m.group("protocol").equalsIgnoreCase("ldaps");

        //ldap admin
        String adminDn = properties.getString("ldap.admin", "");
        String adminPassword = properties.getString("ldap.admin.password", "");
        
        //pool config
        int poolMax =  8;
        int poolMinIdle = 0;
        int poolMaxIdle = 8;
        int poolEvictTests = 3;
        long poolMinEvictIdleTime = 1000L * 60L * 30L;
        
        //set ldap connection config
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(this.ldapServer);
        config.setLdapPort(this.ldapPort);
        config.setUseSsl(this.ldapSecure);
        config.setName(adminDn);
        config.setCredentials(adminPassword);
        
        //Factory that is needed from pool
        DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(config);
        
        //Set pool config
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setLifo(true);
        poolConfig.setMaxTotal(poolMax);
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMinIdle(poolMinIdle);
        poolConfig.setNumTestsPerEvictionRun(poolEvictTests);
        poolConfig.setSoftMinEvictableIdleTimeMillis(-1L);
        poolConfig.setMinEvictableIdleTimeMillis(poolMinEvictIdleTime);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(false);
        poolConfig.setTimeBetweenEvictionRunsMillis(-1L);
        
        //finally create pool
        this.pool = new LdapConnectionPool(new ValidatingPoolableLdapConnectionFactory(factory), poolConfig);
    }
    
    public PooledLdapConnection getPooledConnection() throws LdapException {
        //create pooled connecton
        return new PooledLdapConnection(this.pool);
    }

}
