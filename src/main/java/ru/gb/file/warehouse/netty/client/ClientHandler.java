package ru.gb.file.warehouse.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.gb.file.warehouse.netty.common.dto.BasicResponse;
import ru.gb.file.warehouse.netty.common.dto.GetFilesListResponse;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        BasicResponse response = (BasicResponse) msg;
        if (response instanceof GetFilesListResponse) {
            GetFilesListResponse getFilesListResponse = (GetFilesListResponse) response;
            getFilesListResponse.getList()
                    .forEach(System.out::println);
        } else {
            System.out.println(response.getErrorMessage());
        }
    }
}
