import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SubstringSearch{

    public static int stringDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] stringMatrix = new int[len1+1][len2+1];
        
        for (int x = 0; x <= len1; x++) {
            stringMatrix[x][0] = x;
        }

        for (int y = 0; y <= len2; y++) {
            stringMatrix[0][y] = y;
        }

        for (int x = 1; x <= len1; x++) {
            for (int y = 1; y <= len2; y++) {
                if(s1.charAt(x-1) == s2.charAt(y-1)) {
                    stringMatrix[x][y] = stringMatrix[x-1][y-1];
                }
                else{
                    stringMatrix[x][y] = Math.min(stringMatrix[x-1][y-1] , Math.min(stringMatrix[x-1][y] , stringMatrix[x][y-1] ))+1;
                }
            }
        }

        int distance=stringMatrix[len1][len2];

        return distance;
    }

    public static List<String> stringListFromFile(){
        List<String> stringList= new ArrayList<>();

        try {
            File file = new File("stringList.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringList.add(line);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return stringList;
    }

    public static void main(String[] arg){

        Scanner scanner= new Scanner(System.in);
        String str= scanner.nextLine();
        List<String> stringList= stringListFromFile();

        Map<String, Integer> stringMap= new HashMap<>();

        for(String s: stringList){

            int distance=stringDistance(str,s);
            int maxLength = Math.max(str.length(), s.length());
            float matchingPercentage = (1 - ((float) distance / maxLength)) * 100;
            stringMap.put(s,Math.round(matchingPercentage));
        }

        stringMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 40) //only suggest string which are 40% match
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey()));
    }
}
