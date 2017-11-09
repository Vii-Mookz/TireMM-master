package com.hitachi_tstv.mist.it.tiresmanagement;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * Created by musz on 7/28/2016.
 */
public class TireWebservice {
    private static final String SOAP_ACTION = "formatVehicle#GetFormatVehicleDetail";
    private static final String OPERATION_NAME = "GetFormatVehicleDetail";
    private static final String NAMESPACE = "formatVehicle";
    private static final String URL = "http://service.eternity.co.th/tires/system/CenterService/getFormatVehicleService.php?wsdl";
    private static Object resultRequestSOAP = null;

    public static String formatVehicle(String _vehicleId) {
        SoapObject request = new SoapObject(NAMESPACE, OPERATION_NAME);

        request.addProperty("veh_id", _vehicleId);
        //create envolop
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;

        allowAllSSL();

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.debug = true;

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            resultRequestSOAP = envelope.getResponse();
            Log.i("TST", "soap request " + androidHttpTransport.requestDump);

            return resultRequestSOAP.toString();
        } catch (Exception eX) {
            eX.printStackTrace();
            return null;
        }

    }


    private static TrustManager[] trustManagers;


    private static void allowAllSSL() {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        javax.net.ssl.SSLContext context = null;

        if (trustManagers == null) {
            trustManagers = new javax.net.ssl.TrustManager[]{ new _FakeX509TrustManager() };
        }

        try {
            context = javax.net.ssl.SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            Log.e("allowAllSSL", e.toString());
        }
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

    }


    private static class _FakeX509TrustManager implements TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {

        }
        public  void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateEncodingException {

        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return (true);
        }
        public X509Certificate[] getAcceptedIssuers(){
            return (_AcceptedIssuers);
        }

    }
}
