package ru.sberbank.dao;

import org.jetbrains.annotations.NotNull;

import java.io.*;

public class DaoServer implements Dao {

    private @NotNull
    final File data;

    public DaoServer(@NotNull File data) {
        this.data = data;
    }

    @Override
    public byte[] get(@NotNull String key) throws IOException, NullPointerException {
        BufferedReader reader = new BufferedReader(new FileReader(data));
        StringBuilder res = new StringBuilder();
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            if (tmp.contains("id = " + key + ",")) {
                res.append(tmp);
            }
        }
        return res.toString().getBytes();
    }

    @Override
    public void insert(@NotNull String key, @NotNull byte[] value) throws IOException {
        File outputFile = new File("TmpText");

        BufferedReader reader = new BufferedReader(new FileReader(data));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        String line;
        int code = 0;
        while ((line = reader.readLine()) != null) {
            if (line.contains("id = " + key + ",")) {
                code = 1;
                writer.write("id = " + key + ", data = \"" + new String(value) + "\";\n");
            } else {
                writer.write(line);
                writer.newLine();
            }
        }

        if (code == 0) {
            writer.write("id = " + key + ", data = \"" + new String(value) + "\";\n");
        }

        reader.close();
        writer.close();
        data.delete();
        outputFile.renameTo(data);
    }

    @Override
    public void delete(@NotNull String key) throws IOException {
        File outputFile = new File("TmpText");

        BufferedReader reader = new BufferedReader(new FileReader(data));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.contains("id = " + key + ",")) {
                writer.write(line);
                writer.newLine();
            }
        }

        reader.close();
        writer.close();
        data.delete();
        outputFile.renameTo(data);
    }
}
