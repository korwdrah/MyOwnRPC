package com.ljh.client;

import com.ljh.utils.RPCObj.RPCRequest;
import com.ljh.utils.RPCObj.RPCResponse;

public interface RPClient {
    public RPCResponse sendRequest(RPCRequest request);
}
