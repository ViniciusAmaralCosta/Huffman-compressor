package src;

import java.io.*;

public class HuffmanCoding {

    private static final int BYTE_RANGE = 256; // Intervalo para bytes (0-255)
    private int[] frequencies = new int[BYTE_RANGE]; // Frequências dos bytes
    private ListaEncadeadaSimplesDesordenada<HuffmanNode> nodesList = new ListaEncadeadaSimplesDesordenada<>();

    // Classe do nó da árvore de Huffman
    private class HuffmanNode implements Comparable<HuffmanNode> {
        public byte data;
        public int frequency;
        public HuffmanNode left, right;

        public HuffmanNode(byte data, int frequency) {
            this.data = data;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            return Integer.compare(this.frequency, other.frequency);
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    // Função para calcular as frequências de bytes
    private void calculateFrequencies(File inputFile) throws IOException {
        for (int i = 0; i < BYTE_RANGE; i++) frequencies[i] = 0;

        try (FileInputStream fis = new FileInputStream(inputFile)) {
            int b;
            while ((b = fis.read()) != -1) {
                frequencies[b]++;
            }
        }
    }

    // Construir a árvore de Huffman usando a ListaEncadeadaSimplesDesordenada
    private HuffmanNode buildTree() throws Exception {
        for (int i = 0; i < BYTE_RANGE; i++) {
            if (frequencies[i] > 0) {
                nodesList.guardeNoFinal(new HuffmanNode((byte) i, frequencies[i]));
            }
        }

        while (nodesList.getTamanho() > 1) {
            HuffmanNode min1 = removeMin();
            HuffmanNode min2 = removeMin();

            HuffmanNode combined = new HuffmanNode((byte) 0, min1.frequency + min2.frequency);
            combined.left = min1;
            combined.right = min2;

            insertNode(combined);
        }

        return nodesList.getPrimeiro();
    }

    // Função para remover o nó com menor frequência
    private HuffmanNode removeMin() throws Exception {
        HuffmanNode minNode = nodesList.getPrimeiro();
        nodesList.removaPrimeiro();
        return minNode;
    }

    // Inserir um nó na lista de forma ordenada
    private void insertNode(HuffmanNode node) throws Exception {
        int position = 0;
        for (int i = 0; i < nodesList.getTamanho(); i++) {
            if (node.frequency < nodesList.get(i).frequency) {
                break;
            }
            position++;
        }
        nodesList.guardeEm(position, node);
    }

    // Gerar os códigos de Huffman manualmente
    private void generateCodes(HuffmanNode node, String[] codes, String currentCode) {
        if (node.isLeaf()) {
            codes[node.data & 0xFF] = currentCode;
            return;
        }

        generateCodes(node.left, codes, currentCode + "0");
        generateCodes(node.right, codes, currentCode + "1");
    }

    // Escrever a árvore no arquivo
    private void writeTree(HuffmanNode node, DataOutputStream dos) throws IOException {
        if (node.isLeaf()) {
            dos.writeBoolean(true);
            dos.writeByte(node.data);
        } else {
            dos.writeBoolean(false);
            writeTree(node.left, dos);
            writeTree(node.right, dos);
        }
    }

    // Ler a árvore do arquivo
    private HuffmanNode readTree(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            return new HuffmanNode(dis.readByte(), 0);
        } else {
            HuffmanNode node = new HuffmanNode((byte) 0, 0);
            node.left = readTree(dis);
            node.right = readTree(dis);
            return node;
        }
    }

    // Escrever os dados comprimidos no arquivo
    private void writeCompressedData(FileInputStream fis, DataOutputStream dos, String[] codes, long fileSize) throws IOException {
        int buffer = 0;
        int bufferSize = 0;
        long processed = 0;

        int b;
        while ((b = fis.read()) != -1) {
            String code = codes[b];
            for (char bit : code.toCharArray()) {
                buffer = (buffer << 1) | (bit - '0');
                bufferSize++;

                if (bufferSize == 8) {
                    dos.writeByte(buffer);
                    buffer = 0;
                    bufferSize = 0;
                }
            }

            processed++;
            if (processed % 1000 == 0) {
                System.out.printf("\rCompression Progress: %.2f%%", (processed / (double) fileSize) * 100);
            }
        }

        if (bufferSize > 0) {
            buffer = buffer << (8 - bufferSize);
            dos.writeByte(buffer);
        }

        System.out.println("\nCompression complete.");
    }

    // Ler os dados comprimidos do arquivo
    private void readCompressedData(DataInputStream dis, FileOutputStream fos, HuffmanNode root) throws IOException {
        HuffmanNode currentNode = root;
        int b;

        while ((b = dis.read()) != -1) {
            for (int i = 7; i >= 0; i--) {
                currentNode = ((b >> i) & 1) == 0 ? currentNode.left : currentNode.right;

                if (currentNode.isLeaf()) {
                    fos.write(currentNode.data);
                    currentNode = root;
                }
            }
        }
    }

    // Função para comprimir o arquivo
    public void compressFile(File inputFile, File compressedFile) throws Exception {
        calculateFrequencies(inputFile);

        HuffmanNode root = buildTree();

        String[] codes = new String[BYTE_RANGE];
        generateCodes(root, codes, "");

        try (FileInputStream fis = new FileInputStream(inputFile);
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(compressedFile))) {
            writeTree(root, dos);
            writeCompressedData(fis, dos, codes, inputFile.length());
        }
    }

    // Função para descomprimir o arquivo
    public void decompressFile(File compressedFile, File decompressedFile) throws Exception {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(compressedFile));
             FileOutputStream fos = new FileOutputStream(decompressedFile)) {
            HuffmanNode root = readTree(dis);
            readCompressedData(dis, fos, root);
        }
    }
}
