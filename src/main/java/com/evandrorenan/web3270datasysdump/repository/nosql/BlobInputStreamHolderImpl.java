package com.evandrorenan.web3270datasysdump.repository.nosql;

import com.evandrorenan.web3270datasysdump.domain.exception.Web3270RuntimeException;
import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class BlobInputStreamHolderImpl implements BlobInputStreamHolder {

    public static final int LEN = 1920;
    public static final String INPUT_STREAM_CLOSE_FAILED = "Attempt to close blobInputStream failed. If this keeps happening application stability can be compromised. Exception throwed: {}";
    @NonNull
    InputStream blobInputStream;
    @NonNull
    private Boolean inputStreamEnded;
    @NonNull
    private List<String> bufferedLines;
    @NonNull
    private Integer bufferIndex;
    @NonNull
    private final String leftOverData;

    public BlobInputStreamHolderImpl(InputStream blobInputStream) {
        this.blobInputStream = blobInputStream;
        this.inputStreamEnded = false;
        this.bufferedLines = new ArrayList<>();
        this.bufferIndex = 0;
        this.leftOverData = "";
    }

    @Override
    public void forEachLine(Consumer<String> action) {
        Optional<String> optCurrentLine = this.getNextLine();
        while (optCurrentLine.isPresent()) {
            action.accept(optCurrentLine.get());
            optCurrentLine = this.getNextLine();
        }
    }

    public Optional<String> getNextLine() {

        if (chunckEnded()) {
            getNextChunck();
        }
        bufferIndex++;

        if (inputStreamEnded()) return Optional.empty();
        return Optional.of(this.bufferedLines.get(bufferIndex - 1));
    }

    private boolean inputStreamEnded() {
        return this.inputStreamEnded;
    }

    private void getNextChunck() {
        Optional<BlobChunckHolder> holder = this.getNextChunk();
        holder.ifPresentOrElse(this::handleNextChunck, this::handleInputStreamEnd);
    }

    private void handleInputStreamEnd() {
        this.inputStreamEnded = true;
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

    private boolean chunckEnded() {
        return bufferIndex >= bufferedLines.size();
    }

    public Optional<BlobChunckHolder> getNextChunk() {
        log.info("Will get chunk. Left over data: {}", this.leftOverData);
        try {
            byte[] blobBytes = blobInputStream.readNBytes(LEN);
            if (inputStreamEnded(blobBytes)) return Optional.empty();

            return buildNextChunck(blobBytes);

        } catch (IOException e) {
            log.error("Error trying to read blobInputStream: {}", e.getMessage());
            throw new Web3270RuntimeException(e);
        }
    }

    private Optional<BlobChunckHolder> buildNextChunck(byte[] blobBytes) {
        String chunk = this.leftOverData + new String(blobBytes, StandardCharsets.UTF_8);
        int lastLineBreak = getLastLineBreak(chunk);

        return Optional.of(BlobChunckHolder.builder()
                                           .chunckData(chunk.substring(0, lastLineBreak))
                                           .leftOverData(chunk.substring(lastLineBreak))
                                           .build());
    }

    private boolean inputStreamEnded(byte[] blobBytes) {
        return blobBytes.length == 0 && this.leftOverData.isEmpty();
    }

    private static int getLastLineBreak(String chunk) {
        int lastLineBreak = chunk.lastIndexOf("\n");
        if (lastLineBreak == 0) return chunk.length();

        return lastLineBreak;
    }
}
