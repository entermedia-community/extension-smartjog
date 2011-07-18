importPackage( Packages.com.openedit.util );
importPackage( Packages.java.util );
importPackage( Packages.java.lang );
importPackage( Packages.com.openedit.modules.update );

var war = "http://dev.entermediasoftware.com/jenkins/job/extension-smartjog/lastSuccessfulBuild/artifact/deploy/extension-smartjog.zip";

var root = moduleManager.getBean("root").getAbsolutePath();
var web = root + "/WEB-INF";
var tmp = web + "/tmp";

log.add("1. GET THE LATEST WAR FILE");
var downloader = new Downloader();
downloader.download( war, tmp + "/entermedia-smartjog.zip");

log.add("2. UNZIP WAR FILE");
var unziper = new ZipUtil();
unziper.unzip(  tmp + "/entermedia-smartjog.zip",  tmp );

log.add("3. REPLACE LIBS");
var files = new FileUtils();
files.deleteMatch( web + "/lib/entermedia-smartjog*.jar");
files.deleteMatch( web + "/lib/sjws.jar");
files.deleteMatch( web + "/lib/axis.jar");
files.deleteMatch( web + "/lib/jaxrpc.jar");
files.deleteMatch( web + "/lib/commons-discovery*.jar");
files.deleteMatch( web + "/lib/commons-logging*.jar");
files.deleteMatch( web + "/lib/wsdl4j*.jar");


files.copyFileByMatch( tmp + "/lib/entermedia-smartjog*.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/sjws.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/axis.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/jaxrpc.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/commons-discovery*.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/commons-logging*.jar", web + "/lib/");
files.copyFileByMatch( tmp + "/lib/wsdl4j*.jar", web + "/lib/");
//files.deleteMatch( web + "/bin/windows/aspera/")
//files.copyFileByMatch( tmp + "/bin/windows/aspera", web + "/bin/windows/aspera/");

//files.deleteMatch( web + "/bin/linux/aspera/")//
//files.copyFileByMatch( tmp + "/bin/linux/aspera", web + "/bin/linux/aspera/");



log.add("5. CLEAN UP");
files.deleteAll(tmp);

log.add("6. UPGRADE COMPLETED");