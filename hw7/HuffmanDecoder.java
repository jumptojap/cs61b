/**
 * ClassName: HuffmanDecoder
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 20:46
 * Version: v1.0
 */
public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        /* Read first object from the file. */
        BinaryTrie binaryTrie = (BinaryTrie) or.readObject();
        /* Read second object from the file. */
        Integer size = (Integer) or.readObject();
        BitSequence assemble = (BitSequence) or.readObject();
        char[] arr = new char[size];
        for (int i = 0; i < size; i++) {
            Match match = binaryTrie.longestPrefixMatch(assemble);
            assemble = assemble.allButFirstNBits(match.getSequence().length());
            arr[i] = match.getSymbol();
        }
        FileUtils.writeCharArray(args[1], arr);

    }
}
