package less;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TryTest {
    private String str = "Hello world!";

    @BeforeTest
    public void before(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("file.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("Hello Jenkins. My Version of maven 6.2 buy.");
        writer.close();
    }

    @Test
    public void someTest() {
       String s = readFile("file.txt");
      boolean result =  checkReal(s);

        Assert.assertEquals(result, true);
    }

    @AfterTest
    public void after(){
        try{

            File file = new File("file.txt");

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            e.printStackTrace();

        }
    }
    public String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public boolean checkReal(String str){
        boolean result = false;

        String delims = "[ ]";
        String[] tokens = str.split(delims);
        RealNumber rn = new RealNumber();

        for(String s: tokens){
            if (rn.checkReal(s)){
                result = true;
                return result;
            }
        }
        return result;
    }
}

class RealNumber {

    private Pattern pattern;
    private Matcher matcher;
    private String regex = "[-]?(?:\\d+\\.\\d+)";

    public boolean checkReal(String number) {
        pattern = Pattern.compile(regex);
        matcher = pattern .matcher(number);
        return matcher.matches();
    }
}
