package ru.gb.file.warehouse.netty.common.handler;

import io.netty.channel.ChannelHandlerContext;
import ru.gb.file.warehouse.netty.common.dto.UploadFileRequest;
import ru.gb.file.warehouse.netty.common.dto.UploadFileResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UploadFileHandler implements RequestHandler<UploadFileRequest, UploadFileResponse> {

    private static final String SERVER_PATH = "/Users/bchervoniy/IdeaProjects/file-warehouse/server-dir/";
    private static final Map<ChannelHandlerContext, RandomAccessFile> FILE_DESCRIPTOR_MAP = new ConcurrentHashMap<>();

    @Override
    public UploadFileResponse handle(UploadFileRequest request, ChannelHandlerContext context) {
        String fileName = request.getFileName();
        byte[] filePartData = request.getFilePartData();

        Path newFilePath = Paths.get(SERVER_PATH + fileName);
        File file = newFilePath.toFile();
        RandomAccessFile randomAccessFile = FILE_DESCRIPTOR_MAP.get(context);
        try {
            if (randomAccessFile == null) {
                randomAccessFile = new RandomAccessFile(file, "rw");
                FILE_DESCRIPTOR_MAP.put(context, randomAccessFile);
            }
            randomAccessFile.write(filePartData);
            if (request.isLast()) {
                randomAccessFile.close();
                FILE_DESCRIPTOR_MAP.remove(context);
            }
        } catch (IOException e) {
            return new UploadFileResponse("Не удалось сохранить файл на сервере");
        }
        return new UploadFileResponse("OK");
    }
}
