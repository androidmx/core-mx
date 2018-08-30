package mx.gigigo.core.retrofitextensions;

import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

/**
 * Created by isarael.cortes on 8/28/18.
 */

public class SSLFactory {

    public static void installCertificades(OkHttpClient.Builder builder, SSLBuildProvider buildProvider){
        SSLContext sslContext;
        TrustManager[] trustManagers;
        try {
            trustManagers = getTrustManagers(buildProvider);
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);
        } catch (Exception e) {
            e.printStackTrace(); //TODO replace with real exception handling tailored to your needs
            return;
        }
        builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustManagers[0]);
        validatePreLollipopConfigs(builder, buildProvider);
    }

    public static OkHttpClient.Builder validatePreLollipopConfigs(OkHttpClient.Builder builder, SSLBuildProvider buildProvider) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            TrustManager[] trustManagers;
            try {
                trustManagers = getTrustManagers(buildProvider);
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, trustManagers, null);
                builder.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));
                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                builder.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return builder;
    }

    public static TrustManager[] getTrustManagers(SSLBuildProvider buildProvider) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        InputStream certInputStream = buildProvider.getCertificadeInputStream();
        BufferedInputStream bis = new BufferedInputStream(certInputStream);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        while (bis.available() > 0) {
            Certificate cert = certificateFactory.generateCertificate(bis);
            keyStore.setCertificateEntry(buildProvider.getCertificadeEntryName(), cert);
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }

    public interface SSLBuildProvider{
        InputStream getCertificadeInputStream() throws IOException;
        String getCertificadeEntryName();
    }

    static class Tls12SocketFactory extends SSLSocketFactory {

        public final String[] TLS_V12_ONLY = {"TLSv1.2"};
        final SSLSocketFactory delegate;

        public Tls12SocketFactory(SSLSocketFactory base) {
            this.delegate = base;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return patch(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
            return patch(delegate.createSocket(host, port, localHost, localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return patch(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return patch(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket patch(Socket s) {
            if (s instanceof SSLSocket) {
                ((SSLSocket) s).setEnabledProtocols(TLS_V12_ONLY);
            }
            return s;
        }
    }
}
