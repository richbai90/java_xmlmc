package com.bittercreektech.xmlmc.types;

import com.bittercreektech.xmlmc.ComplexParam;
import com.bittercreektech.xmlmc.Request;

/**
 * Very simple interfacing forcing every SwType to implement the buildXml method that returns a ComplexParam type
 * This way the request can accept any swType natively without having to convert it first into a ComplexParam
 */
public interface SwType {
    /**
     * Parse the information in the class into a ComplexParam suitable for passing to a {@link Request}.
     * @return {@link ComplexParam}
     */
    public ComplexParam buildXml();
}
