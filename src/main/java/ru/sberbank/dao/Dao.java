package ru.sberbank.dao;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface Dao {
    byte[] get(@NotNull String key) throws NoSuchFieldException, IOException;

    void insert(@NotNull String key, @NotNull byte[] value) throws IOException;

    void delete(@NotNull String key) throws IOException;
}
