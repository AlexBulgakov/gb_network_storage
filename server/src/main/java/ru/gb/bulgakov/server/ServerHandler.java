package ru.gb.bulgakov.server;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Handles a server-side channel.
 */

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

            FileMessage fileMessage = (FileMessage) msg;
            Path pathToNewFile = Paths.get("server/NetworkStorage/" + File.separator + fileMessage.getFileName());
            if (fileMessage.isDirectory() || fileMessage.isEmpty()) {
                if (Files.exists(pathToNewFile)) {
                    System.out.println("Файл с таким именем уже существует");
                } else {
                    Files.createDirectory(pathToNewFile);
                }
            } else {
                if (Files.exists(pathToNewFile)) {
                    System.out.println("Файл с таким именем уже существует");
                } else {
                    Files.write(Paths.get("ServerSide/CloudStorage/" + fileMessage.getLogin() + File.separator + fileMessage.getFileName()), fileMessage.getData(), StandardOpenOption.CREATE);
                }
            }
            ctx.writeAndFlush();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

