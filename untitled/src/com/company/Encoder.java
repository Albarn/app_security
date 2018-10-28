package com.company;

public interface Encoder {
    byte[] encode(byte[] key, byte[] message);

    byte[] decode(byte[] key, byte[] message);
}
