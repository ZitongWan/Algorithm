package Algorithm;

public class KMP {
    public static void main(String[] args) {
        String target = "BBC ABCDAB ABCDABCDABDE";
        String pattern = "ABCDABD";
        int result = kmpSearch(target, pattern);
        if (result != -1) {
            System.out.println("Match found starting at index: " + result);
        } else {
            System.out.println("No match found...");
        }
    }

    private static int kmpSearch(String str1, String str2) {
        int i = 0;      // Pointer for str1
        int j = 0;      // Pointer for str2, also represents the length of the common prefix and suffix
        int count = -1; // Record the final index of the successful match
        int[] next = getNextArr(str2); // Get the character match table (next array)
        while (i != str1.length() && j != str2.length()) {
            if (str1.charAt(i) == str2.charAt(j)) { // If characters match, continue to the next match
                i++;
                j++;
                if (j == str2.length()) { // If the pointer of str2 has reached the end, the match is complete
                    count = i - j; // Return the starting pointer position of the match, which is the index value
                }
            } else { // If the match fails, consider two situations
                if (j != 0) { // If there was a previous successful match, skip [original(i) + (matched length - next[j])]
                    i = (i - j) + (j - next[j - 1]); // This expression represents the complete form, but it can be simplified to i - next[j - 1]
                    j = 0;
                } else { // If there was no previous successful match, i++, j stays the same
                    i++;
                }
            }
        }
        return count; // Return the index value where the match ends
    }

    private static int[] getNextArr(String str) {
        // Calculate the next array
        int strLen = str.length(); // Record the length of the string
        int[] next = new int[strLen]; // The capacity of the next array is the same as the length of the string
        next[0] = 0;
        int i = 1;
        int prefixLen = 0; // Length of the common prefix and suffix
        while (i != strLen) { // Loop termination condition
            if (str.charAt(prefixLen) == str.charAt(i)) { // If they are the same
                prefixLen++; // Increase the length of the common prefix and suffix
                next[i] = prefixLen;
                i++;
            } else { // If there is no common prefix and suffix
                if (prefixLen == 0) {
                    next[i] = 0;
                    i++;
                } else {
                    prefixLen = next[prefixLen - 1];
                    /*For example, if the str is ABACABAB, the nextArr is [0,0,1,0,1,2,3,2]
                    When str.charAt(i) == B(the final number), it is obviously that B != C,so prefix_len = nextArr[prefix_len - 1] = 1 + ?
                    And then because str.charAt(1) = B = str.charAt(i) == B, so prefix_len == 1 + 1 = 2
                    So during the final process,str.charAt(i) = B, which is not changed while prefix_len is changing.
                    It stops until (str.charAt(prefix_len) == str.charAt(i)) or (prefix_len == 0)
                    * */
                }
            }
        }
        return next;
    }
}

class ViolentMatching{
    private static int count(String str1, String str2){
        int str1len = str1.length();  // Pointer for str1
        int str2len = str2.length();

        int count = -1;  // return value

        int i = 0;  // Pointer for str1
        int j = 0;  // Pointer for str2
        while ((i != str1len) && (j != str2len)){
            if (str1.charAt(i) == str2.charAt(j)){  // If the match is successful
                i++;
                j++;
            } else{  // Matching failed
                i = (i - j) + 1;  // i backtrack to the next position where the matching starts
                j = 0;
            }
            if (j == str2len){  // If the match is completed, return the index value of the successful match. If it fails, -1 remains unchanged
                count = i - j;
            }
        }
        return count;
    }
}
