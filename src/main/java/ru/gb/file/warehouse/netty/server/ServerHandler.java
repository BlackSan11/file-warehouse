package ru.gb.file.warehouse.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.gb.file.warehouse.netty.common.dto.BasicRequest;
import ru.gb.file.warehouse.netty.common.dto.BasicResponse;
import ru.gb.file.warehouse.netty.common.handler.HandlerRegistry;
import ru.gb.file.warehouse.netty.common.handler.RequestHandler;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        BasicRequest request = (BasicRequest) msg;
        RequestHandler handler = HandlerRegistry.getHandler(request.getClass());
        BasicResponse response = handler.handle(request);
        channelHandlerContext.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

}
