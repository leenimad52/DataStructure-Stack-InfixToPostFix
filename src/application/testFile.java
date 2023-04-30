package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class testFile {
    public static void main(String[] args) throws Exception {
     
   //  String st=getEquations(new File("\"C:\\data\\file6.242\""));
   //  System.out.println(check(st));    
   //  String st2=getFiles2(new File("\"C:\\data\\file6.242\""));
   //  System.out.println(st2);
    // System.out.println(readfromfile(new File("C:\\data\\file6.242")));
   //  System.out.println(getEquations1(new File("C:\\data\\file6.242")));
     System.out.println(getEquations1(new File("C:\\data\\file6.242")));


/*     String[] lines = st.split("\n");
       for (int i=0 ; i<lines.length ; i++)
       {
    	   String value=Methods.infixToPostfix(lines[i]);
    	   if( Methods.balanced(value) &&  Methods.isValid(value) ) {
    	   System.out.println(lines[i]+" postfix>>>> "+ value+
    			   " evalu>>>> "+ Methods.evaluation(value));}
    	   else if( Methods.balanced(value)==false)
    	   {
    		   System.out.println(lines[i]+">>> unbalanced");
    	   }
    	   else if( Methods.isValid(value)==false)
    	   {
    		   System.out.println(lines[i]+">>> invalid");
    	   }
    	   
       }*/
	
     // String fileName = "file2.242";
     // System.out.println(findFile(fileName));

   //  String fileName = "myfile.txt";
    
    } 
    //////////////////////////////////////////////////////////////////////////////////////////////
    public static String getEquations1(File file) {
        String st=readfromfile(file);
        String equations = "";
        // Find the start and end indices of the 'equations' element
        int eqStart = st.indexOf("<equations>");
        int equEnd = st.indexOf("</equations>");
        if (eqStart != -1 && equEnd != -1) {
            //  the contents of the 'equations' element
            String equValue = st.substring(eqStart, equEnd + "</equations>".length());
            // Find the start and end indices of each 'equation' element
            int StartIndex = equValue.indexOf("<equation>");
            while (StartIndex != -1) {
                int equationEnd = equValue.indexOf("</equation>", StartIndex);
                if (equationEnd == -1) {
                  //  System.out.println("missing tag");
                    String equation = "missing tag";
          		    equations += equation + "\n";
                    break;
                }
                if (equationEnd != -1) {
                    // Extract the contents of the 'equation' element
                    String equationElement = equValue.substring(StartIndex, equationEnd + "</equation>".length());
                    // Extract the equation
                    String equation = equationElement.substring("<equation>".length(), equationElement.length()-"</equation>".length());
                    		  equations += equation + "\n";
                }
               
                // Find the start index of the next 'equation' element
                StartIndex = equValue.indexOf("<equation>", equationEnd);
            }

        }
        return equations;
    }

    /////////////////////////////////////////////////////////////////////////
    public static String findFile(String str)
    {
    	 String fileName = str;
         File rootDir = new File("/");  // Start the search in the root directory

         Queue queue = new Queue();  // Create a queue to store the directories to search
         queue.enqueue(rootDir);  // Add the root directory to the queue

         while (!queue.isEmpty()) {  // While there are still directories to search
             File dir = queue.dequeue();  // Get the next directory
             File[] files = dir.listFiles();  // Get the list of files in the directory

             if (files != null) {  // If the list of files is not null
                 for (File file : files) {  // Iterate through the list of files
                     if (file.isDirectory()) {  // If the file is a directory
                         queue.enqueue(file);  // Add the directory to the queue
                     } else if (file.getName().equals(fileName)) {  // If the file is the one we are looking for
                         System.out.println("File found at: " + file.getAbsolutePath());  // Print the file path
                         return file.getAbsolutePath();
                     }
                 }
             }
             
             }
         return null;
    }
    ///////////////////////////

    
public static String readfromfile (File file)
{
	 String st = "";
     try (FileReader fr = new FileReader(file)) {
         int c;
         while ((c = fr.read()) != -1) {
             st += (char) c;
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return st;
}
//////////////////////////////////////

    public static String getFiles(File fi) {
    	String xml=readfromfile(fi);
        String files = "";

        // Find the start and end indices of the 'files' element
        int filesStartIndex = xml.indexOf("<files>");
        int filesEndIndex = xml.indexOf("</files>");
        if (filesStartIndex != -1 && filesEndIndex != -1) {
            // Extract the contents of the 'files' element
            String filesElement = xml.substring(filesStartIndex, filesEndIndex + "</files>".length());

            // Find the start and end indices of each 'file' element
            int fileStartIndex = filesElement.indexOf("<file>");
            while (fileStartIndex != -1) {
                int fileEndIndex = filesElement.indexOf("</file>", fileStartIndex);
                if (fileEndIndex != -1) {
                    // Extract the contents of the 'file' element
                    String fileElement = filesElement.substring(fileStartIndex, fileEndIndex + "</file>".length());
                    // Extract the file
                    String file = fileElement.substring("<file>".length(), fileElement.length() - "</file>".length());
                    files += file + "\n";
                }

                // Find the start index of the next 'file' element
                fileStartIndex = filesElement.indexOf("<file>",fileEndIndex);
            }
        }return files;
    }
//////////////////////////////////////
public static String getEquations(File file) {
	String xml=readfromfile(file);
    String equations = "";

    // Find the start and end indices of the 'equations' element
    int equationsStartIndex = xml.indexOf("<equations>");
    int equationsEndIndex = xml.indexOf("</equations>");
    if (equationsStartIndex != -1 && equationsEndIndex != -1) {
        // Extract the contents of the 'equations' element
        String equationsElement = xml.substring(equationsStartIndex, equationsEndIndex + "</equations>".length());

        // Find the start and end indices of each 'equation' element
        int equationStartIndex = equationsElement.indexOf("<equation>");
        while (equationStartIndex != -1) {
            int equationEndIndex = equationsElement.indexOf("</equation>", equationStartIndex);
            if (equationEndIndex != -1) {
                // Extract the contents of the 'equation' element
                String equationElement = equationsElement.substring(equationStartIndex, equationEndIndex + "</equation>".length());
                // Extract the equation
                String equation = equationElement.substring("<equation>".length(), equationElement.length() - "</equation>".length());
                equations += equation + "\n";
            }

            // Find the start index of the next 'equation' element
            equationStartIndex = equationsElement.indexOf("<equation>", equationEndIndex);
        }
    }

    return equations;
}

////////////////////////
public static String check(String s) throws Exception {
    String[] lines = s.split("\n");
    String re = "";
    for (int i = 0; i < lines.length; i++) {
        String value = Methods.infixToPostfix(lines[i]);
        if (Methods.balanced(value) && Methods.isValid(value)) {
            re += (lines[i] + " postfix>>>> " + value +
                   " evalu>>>> " + Methods.evaluation(value) + "\n");
        } else if (Methods.balanced(value) == false) {
            re += (lines[i] + ">>> unbalanced\n");
        } else if (Methods.isValid(value) == false) {
            re += (lines[i] + ">>> invalid\n");
        }
    }
    return re;
}

public static String getFiles2(File fi) {
    String st=readfromfile(fi);
    String files = "";

    // Find the start and end indices of each 'file' element
    int fileStart = st.indexOf("<file>");
    while (fileStart != -1) {
        int fileEnd = st.indexOf("</file>", fileStart);
        if (fileEnd != -1) {
            // Extract the contents of the 'file' element
            String fileElement = st.substring(fileStart, fileEnd + "</file>".length());
            // Extract the file name
            int startIndex = fileElement.indexOf("<file>") + "<file>".length();
            int endIndex = fileElement.indexOf("</file>");
            String fileName = fileElement.substring(startIndex, endIndex);

            // Extract the file name without the path
            int lastSlashIndex = fileName.lastIndexOf("\\");
            if (lastSlashIndex != -1) {
                fileName = fileName.substring(lastSlashIndex + 1);
            }

            // Add the file name to the 'files' string
            files += fileName + "\n";
        }
        // Find the start index of the next 'file' element
        fileStart = st.indexOf("<file>",fileEnd);
    }

    return files;
}






}
     