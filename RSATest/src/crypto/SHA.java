package crypto;

import java.nio.ByteBuffer;

public class SHA {

    // hash table.
    private int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b,
            0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01,
            0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7,
            0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152,
            0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147,
            0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc,
            0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819,
            0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08,
            0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f,
            0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    // constant init hash value.
    private int[] initHashValue = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
    };

    //working arrays
    private int[] w = new int[64];
    private int[] h = new int[8];
    private int[] temp = new int[8];

    public byte[] hash(byte[] message){
        // let H = H0
        System.arraycopy(initHashValue, 0, h, 0, initHashValue.length);

        // initialize all words
        int[] words = byte2intArray(pad(message));

        // enumerate all blocks (each containing 16 words)
        for (int i = 0, n = words.length / 16; i < n; ++i) {

            // initialize W from the block's words
            System.arraycopy(words, i * 16, w, 0, 16);
            for (int t = 16; t < w.length; ++t) {
                w[t] = smallSig1(w[t - 2]) + w[t - 7] + smallSig0(w[t - 15])
                        + w[t - 16];
            }

            // let TEMP = H
            System.arraycopy(h, 0, temp, 0, h.length);

            // operate on TEMP
            for (int t = 0; t < w.length; ++t) {
                int t1 = temp[7] + bigSig1(temp[4])
                        + choose(temp[4], temp[5], temp[6]) + k[t] + w[t];
                int t2 = bigSig0(temp[0]) + majority(temp[0], temp[1], temp[2]);
                System.arraycopy(temp, 0, temp, 1, temp.length - 1);
                temp[4] += t1;
                temp[0] = t1 + t2;
            }

            // add values in TEMP to values in H
            for (int t = 0; t < h.length; ++t) {
                h[t] += temp[t];
            }

        }

        return int2byteArray(h);

    }

    /**
     * Padding message to 512bits. Padded message include message, 1-bit, k 0-bits,
     * message length as a 64-bit integer.
     *
     * @param message The message to pad
     * @return new byte array with padded message.
     */
    public byte[] pad(byte[] message)
    {
        final int blockBits = 512;
        final int blockBytes = blockBits / 8;

        // new message length: original + 1-bit and padding + 8-byte length
        int newMessageLength = message.length + 1 + 8;
        int padBytes = blockBytes - (newMessageLength % blockBytes);
        newMessageLength += padBytes;

        // copy message to extended array
        final byte[] paddedMessage = new byte[newMessageLength];
        System.arraycopy(message, 0, paddedMessage, 0, message.length);

        // write 1-bit
        paddedMessage[message.length] = (byte) 0b10000000;

        // skip padBytes many bytes (they are already 0)

        // write 8-byte integer describing the original message length
        int lenPos = message.length + 1 + padBytes;
        ByteBuffer.wrap(paddedMessage, lenPos, 8).putLong(message.length * 8);

        return paddedMessage;
    }

    /**
     * Convert byte array to int array.
     *
     * @param bytes array to converted.
     * @return new int array converted by int
     */
    public int[] byte2intArray(byte[] bytes){
        ByteBuffer buf = ByteBuffer.wrap(bytes);

        int[] result = new int[bytes.length / Integer.BYTES];
        for (int i = 0; i < result.length; i++) {
            result[i] = buf.getInt();
        }

        return result;
    }

    /**
     * Convert int array to byte array
     *
     * @param ints array to converted
     * @return new byte array converted by byte
     */
    public byte[] int2byteArray(int[] ints){
        ByteBuffer buf = ByteBuffer.allocate(ints.length * Integer.BYTES);
        for (int i = 0; i < ints.length; i++) {
            buf.putInt(ints[i]);
        }

        return buf.array();
    }

    /**
     * Choose function.
     *
     * @param x operand1
     * @param y operand2
     * @param z operand3
     * @return choosed result.
     */
    private int choose(int x, int y, int z){
        return (x & y) | ((~x) & z);
    }

    /**
     * Find majority.
     * Or each and operating result.
     *
     * @param x operand1
     * @param y operand2
     * @param z operand3
     * @return operating result
     */
    private int majority(int x, int y, int z){
        return (x & y) | (x & z) | (y & z);
    }

    private static int bigSig0(int x) {
        return Integer.rotateRight(x, 2)
                ^ Integer.rotateRight(x, 13)
                ^ Integer.rotateRight(x, 22);
    }

    private static int bigSig1(int x) {
        return Integer.rotateRight(x, 6)
                ^ Integer.rotateRight(x, 11)
                ^ Integer.rotateRight(x, 25);
    }

    private static int smallSig0(int x) {
        return Integer.rotateRight(x, 7)
                ^ Integer.rotateRight(x, 18)
                ^ (x >>> 3);
    }

    private static int smallSig1(int x) {
        return Integer.rotateRight(x, 17)
                ^ Integer.rotateRight(x, 19)
                ^ (x >>> 10);
    }
}
