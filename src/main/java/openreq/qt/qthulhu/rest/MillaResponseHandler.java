/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openreq.qt.qthulhu.rest;

import com.atlassian.sal.api.net.Response;
import com.atlassian.sal.api.net.ResponseException;
import com.atlassian.sal.api.net.ReturningResponseHandler;

/**
 *
 * @author ttlaurin
 * @param <RET>
 */
public class MillaResponseHandler<RET> implements ReturningResponseHandler<Response, RET> {
    
    private final Class<RET> entity;

    public MillaResponseHandler(Class<RET> entity) {
        this.entity = entity;
    }

    @Override
    public RET handle(Response response) throws ResponseException {
        return response.getEntity(entity);
    }
    
}
