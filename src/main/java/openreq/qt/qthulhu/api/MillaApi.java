package openreq.qt.qthulhu.api;

public interface MillaApi
{
    String getResponseFromMilla(String urlTail, String body, boolean isPost);
}