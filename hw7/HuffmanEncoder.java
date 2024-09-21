import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: HuffmanEncoder
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 20:27
 * Version: v1.0
 */
public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
        for (int i = 0; i < inputSymbols.length; i++) {
            if (frequencyTable.containsKey(inputSymbols[i])) {
                int temp = frequencyTable.get(inputSymbols[i]) + 1;
                frequencyTable.put(inputSymbols[i], temp);
            } else {
                frequencyTable.put(inputSymbols[i], 1);
            }
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        char[] arr = FileUtils.readFile(args[0]);
        Map<Character, Integer> characterIntegerMap = buildFrequencyTable(arr);
        BinaryTrie binaryTrie = new BinaryTrie(characterIntegerMap);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(binaryTrie);
        ow.writeObject(arr.length);
        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> list = new ArrayList<>();
        for (char c : arr) {
            list.add(lookupTable.get(c));
        }
        BitSequence assemble = BitSequence.assemble(list);
        ow.writeObject(assemble);

    }
}
