package com.kernel360.common.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface RestDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(modifyUris().scheme("https")
                                             .host("devapi.washfit.site")
                                             .removePort(), prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
