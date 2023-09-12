package com.evandrorenan.web3270datasysdump.repository.nosql;

import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
public class BlobInputStreamHolder {
    public static final String INPUT_STREAM_CLOSE_FAILED = "Attempt to close blobInputStream failed. If this keeps happening application stability can be compromised. Exception throwed: {}";
    private final InputStream blobInputStream;
    private final BlobRepository repo;
    private Boolean endReached;
    private List<String> bufferedLines= new ArrayList<>();
    private Integer bufferIndex = 0;

    public BlobInputStreamHolder(InputStream blobInputStream, BlobRepository repo) {
        this.blobInputStream = blobInputStream;
        this.repo = repo;
    }

    public boolean endReached() {
        return endReached != null && endReached;
    }

    public String nextLine() {
        if (endReached) throw new IndexOutOfBoundsException("Input Stream already reach it's end.");
        if (bufferOverflow()) {
            Optional<BlobChunckHolder> holder = repo.nextChunk(blobInputStream);
            holder.ifPresentOrElse(this::handleNextChunck, this::handleInputStreamEnd);
        }
        bufferIndex++;
        return this.endReached() ? "" : this.bufferedLines.get(bufferIndex - 1);
    }

    private void handleInputStreamEnd() {
        this.endReached = true;
        try {
            this.blobInputStream.close();
        } catch (IOException e) {
            log.warn(INPUT_STREAM_CLOSE_FAILED, e.getMessage());
        }
    }

    private void handleNextChunck(BlobChunckHolder holder) {
        this.bufferedLines = Arrays.asList(holder.chunckData().split("\n"));
        bufferIndex = 0;
    }

    private boolean bufferOverflow() {
        return bufferIndex >= bufferedLines.size();
    }
}
