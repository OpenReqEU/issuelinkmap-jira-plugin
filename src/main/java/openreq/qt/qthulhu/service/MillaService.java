package openreq.qt.qthulhu.service;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.net.Request;
import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.sal.api.net.ResponseException;

import javax.inject.Inject;
import javax.inject.Named;
import openreq.qt.qthulhu.api.MillaApi;
import openreq.qt.qthulhu.rest.MillaResponseHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExportAsService ({MillaApi.class})
@Named ("millaService")
public class MillaService implements MillaApi
{
    private final MillaResponseHandler responseHandler;

    private final RequestFactory requestFactory;


    @Inject
    public MillaService(@ComponentImport RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
        this.responseHandler = new MillaResponseHandler(String.class);
    }


    // method to handle out-going rest-call to OpenReq infra in localhost
    @Override
    public String getResponseFromMilla(String urlTail, String body, boolean isPost) {
        String millaAddress = "https://bugreports-test.qt.io/rest/fisutankki/1";
//        String millaAddress = "http://localhost:9203";
        String completeAddress = millaAddress + urlTail;

        //System.out.println("milla addr: " + completeAddress);

        Request request = null;

        if (isPost) {
            request = requestFactory.createRequest(Request.MethodType.POST, completeAddress);
            request.setRequestBody(body);
        } else {
            request = requestFactory.createRequest(Request.MethodType.GET, completeAddress);
        }

        String response = null;

        try {
            request.setSoTimeout(10000000);
            response = (String)request.executeAndReturn(this.responseHandler);
            System.out.println("request executed");
        } catch (ResponseException ex) {
            System.out.println("ERROR: " + ex);
            Logger.getLogger(MillaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("milla return " + response);
        return response;

    }
}