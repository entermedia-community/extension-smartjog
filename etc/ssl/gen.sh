rm -rf stores
mkdir stores
keytool -genkey -alias entermedia-smartjog -validity 2100 -keystore stores/entermedia.jks < keystore.conf
