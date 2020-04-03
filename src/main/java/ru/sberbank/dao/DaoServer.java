package ru.sberbank.dao;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DaoServer implements Dao {
    @Override
    public byte[] get(@NotNull String key) throws NoSuchFieldException, IOException {
        return new byte[0];
    }

    @Override
    public void insert(@NotNull String key, @NotNull byte[] value) throws IOException {

    }

    @Override
    public void delete(@NotNull String key) throws IOException {

    }
}
