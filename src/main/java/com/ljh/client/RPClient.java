package com.ljh.client;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;

public interface RPClient {
    public RPCResponse sendRequest(RPCRequest request);
}
