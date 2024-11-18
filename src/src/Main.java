package src;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File inputFile = new File("video2s.mp4");
        File compressedFile = new File("compressed.huff");
        File decompressedFile = new File("decompressed.mp4");

        HuffmanCoding huffman = new HuffmanCoding();

        try {
            System.out.println("Starting compression...");
            huffman.compressFile(inputFile, compressedFile);

            System.out.println("Starting decompression...");
            huffman.decompressFile(compressedFile, decompressedFile);

            System.out.println("Process completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
