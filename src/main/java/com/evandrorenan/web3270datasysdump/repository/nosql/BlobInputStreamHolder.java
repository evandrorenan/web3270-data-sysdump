package com.evandrorenan.web3270datasysdump.repository.nosql;

import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Builder
@Slf4j
public class BlobInputStreamHolder {
    private final InputStream blobInputStream;
    private final BlobRepository repo;
    private Boolean endReached;
    private List<String> bufferedLines= new ArrayList<>();
    private Integer bufferIndex = 0;

    public BlobInputStreamHolder(InputStream blob, BlobRepository repo) {
        this.blobInputStream = blob;
        this.repo = repo;
    }

    public boolean endReached() {
        return endReached != null && endReached;
    }

    public String nextLine() {
        if (bufferOverflow()) {
            Optional<BlobChunckHolder> holder = repo.nextChunk(blobInputStream);
            holder.ifPresentOrElse(this::handleNextChunck, this::handleInputStreamEnd);
        }
        bufferIndex++;
        return this.endReached() ? "" : this.bufferedLines.get(bufferIndex - 1);
    }

    private void handleInputStreamEnd() {
        this.endReached = true;
    }

    private void handleNextChunck(BlobChunckHolder holder) {
        this.bufferedLines = Arrays.asList(holder.chunckData().split("\n"));
        bufferIndex = 0;
    }

    private boolean bufferOverflow() {
        return bufferIndex >= bufferedLines.size();
    }
}
