package com.evandrorenan.web3270datasysdump.infrastructure.adapters;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;

import java.util.List;
import java.util.function.Consumer;

public interface BlobInputStreamHolder {

    void forEachLine(Consumer<String> action);

}
