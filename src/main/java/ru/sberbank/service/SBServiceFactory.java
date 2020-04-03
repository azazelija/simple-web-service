package ru.sberbank.service;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

final class SBServiceFactory{

    private SBServiceFactory() {
    }

    /**
     * Construct a storage instance.
     *
     * @param port port to bind HTTP server to
     * @param data local disk folder to persist the data to
     * @return a storage instance
     */
    @NotNull
    static SBService create(final int port, @NotNull final File data) throws IOException {

        if (port <= 0 || 65536 <= port) {
            throw new IllegalArgumentException("Port out of range");
        }

        if (!data.exists()) {
            throw new IllegalArgumentException("Path doesn't exist: " + data);
        }

        return new SBServer(port, data);
    }
}

