package org.blockserver.net.internal.request;

/**
 * Represents an internal DisconnectRequest.
 */
public class DisconnectRequest extends InternalRequest{
    //Message is not supported in PE Protocol
    public String reason;
}
