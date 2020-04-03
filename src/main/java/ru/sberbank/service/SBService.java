package ru.sberbank.service;

public interface SBService {
    /**
     * Привязать хранилище к HTTP-порту и начать прослушивание.
     * <p>
     * Может быть вызван только один раз.
     */
    public void start();

    /**
     * Прекратите слушать и освободите все ресурсы.
     * <p>
     * Может вызываться только один раз и после {@link #start ()}.
     */
    public void stop();

}
