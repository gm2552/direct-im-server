package com.cerner.healthe.direct.im.springconfig;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class XMPPServerConfig
{
	protected static final String OPENFIRE_HOME_PROP = "openfireHome";
	
	protected static final String OPENFIRE_TEMPLATES_LOC = "/confTemplates/";
	
	@Value("${openfire.home:.}")
	protected String openFireHome;
	
	@Bean
	@ConditionalOnMissingBean
	public XMPPServer xmppServer() throws Exception
	{
		System.setProperty(OPENFIRE_HOME_PROP, openFireHome);
		
		writeOpenFireConfig();
		
		writeSecurityConfig();
		
		writePlugins();
		
		final XMPPServer server = new XMPPServer();
		
		return server;
	}
	
	protected void 	writeOpenFireConfig() throws Exception
	{
		final File file = new File("conf/openfire.xml");
		
		if (!file.exists())
		{	
			String openFirePropString = IOUtils.resourceToString(OPENFIRE_TEMPLATES_LOC + "openfire.xml", Charset.defaultCharset());
		
			FileUtils.write(file, openFirePropString, Charset.defaultCharset());
		}
		else
		{
			// check to see if the admin console port has been shutdown
			String str = FileUtils.readFileToString(file, Charset.defaultCharset());
			str = str.replaceAll("<port>-1</port>","<port>9090</port>");
			str = str.replaceAll("<securePort>-1</securePort>","<securePort>9091</securePort>");
			
			FileUtils.writeStringToFile(file, str, Charset.defaultCharset(), false);
		}
	}
	
	protected void writeSecurityConfig() throws Exception
	{
		final File file = new File("conf/security.xml");
		
		if (!file.exists())
		{
			String securityFilePropString = IOUtils.resourceToString(OPENFIRE_TEMPLATES_LOC + "security.xml", Charset.defaultCharset());
		
			FileUtils.write(file, securityFilePropString, Charset.defaultCharset());
		
			CopyResourcesFromClassPathToFilesystemDirectory("resources/security", "./resources/security");
		}
	}
	
	protected void writePlugins() throws Exception
	{
		
		final File file = new File("plugins");
		
		if (!file.exists())
		{
			CopyResourcesFromClassPathToFilesystemDirectory("plugins", "./plugins");		
		}
	}
	
	protected void CopyResourcesFromClassPathToFilesystemDirectory(String source, String dest) throws Exception
	{
		
		final File directory = new File(dest);
		directory.mkdirs();
		FileUtils.cleanDirectory(directory);
		
		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + source + "/**");
        for (Resource resource : resources) 
        {
            if (resource.exists() & resource.isReadable() && resource.contentLength() > 0) 
            {
                final URL url = resource.getURL();
                final String urlString = url.toExternalForm();
                final String targetName = urlString.substring(urlString.indexOf(source));
                File destination = new File(targetName);
                FileUtils.copyURLToFile(url, destination);
            } 
        }
        
	}
}
