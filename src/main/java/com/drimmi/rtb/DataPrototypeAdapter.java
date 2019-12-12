package com.drimmi.rtb;

import java.nio.ByteBuffer;

public interface DataPrototypeAdapter<T> {

    DataPrototypeAdapter<T> clone();

    String asString();

    ByteBuffer toBinary();
}
