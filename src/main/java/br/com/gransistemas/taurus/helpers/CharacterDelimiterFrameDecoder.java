package br.com.gransistemas.taurus.helpers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class CharacterDelimiterFrameDecoder extends DelimiterBasedFrameDecoder {
    private static ByteBuf createDelimiter(char delimiter) {
        byte[] buf = {(byte) delimiter};
        return Unpooled.wrappedBuffer(buf);
    }

    private static ByteBuf createDelimiter(String delimiter) {
        byte[] buf = new byte[delimiter.length()];
        for (int i = 0; i < delimiter.length(); i++) {
            buf[i] = (byte) delimiter.charAt(i);
        }
        return Unpooled.wrappedBuffer(buf);
    }

    public CharacterDelimiterFrameDecoder(int maxFrameLength, char delimiter) {
        super(maxFrameLength, createDelimiter(delimiter));
    }

    public CharacterDelimiterFrameDecoder(int maxFrameLength, String delimiter) {
        super(maxFrameLength, createDelimiter(delimiter));
    }
}
