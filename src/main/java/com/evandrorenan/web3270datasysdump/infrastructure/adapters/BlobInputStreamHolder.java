package com.evandrorenan.web3270datasysdump.infrastructure.adapters;

import java.util.function.Consumer;

public interface BlobInputStreamHolder {

    void forEachLine(Consumer<String> action);

}
