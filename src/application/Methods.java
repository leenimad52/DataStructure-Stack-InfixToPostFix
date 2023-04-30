package application;

import java.io.File;
import java.io.FileReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Methods {
	
	////////////////////////////////////////////////////////////////////
	static int pri (char c ) {
		if (c== '-' || c== '+') {
			return 1 ;
		}
		else if (c== '/' || c== '*') {
			return 2 ;

		}
		else if (c=='^') {
			return 3 ;
		}
		else {
			return 0 ;
		}
	}
	////////////////////////////////////////////////////////////////////////
	static String infixToPostfix (String e ) {
		CursorStack stack=new CursorStack();// Create a stack for storing operators
		int s = stack.alloc();
        String output = "" ;
      for (int i = 0 ; i < e.length() ; i++) {
	  if (e.charAt(i) == 32) {  // If the character is a space, skip to the next character
 		  continue ;
 	  }
 	  if (Character.isDigit(e.charAt(i))) {
 		  // If the character is a letter or digit, add it to the output string
 		  output+=e.charAt(i) ;
 	  }
 	  else if (e.charAt(i) == '(') {
 		  stack.push(s,e.charAt(i)) ;
 	  }
 	  else if (e.charAt(i) == ')') {
 		 // pop  from the stack and add them to the output string until an opening parenthesis is find
 		  while ((char)stack.getTop(s) != '(') {
 			  output += stack.getTop(s) ;
 			  stack.pop(s) ;
 		  }
 		  stack.pop(s) ; //Discard the opening parenthesis

 	  }
 	  else {// If the character is an operator
 		  // pop operators from the stack and add them to the output string
          // as long as they have higher or equal precedence to the current operator
 		  while (!stack.isEmpty(s) && pri(e.charAt(i)) <= pri((char)stack.getTop(s))) {//true
 			  output += stack.getTop(s) ;
 			  stack.pop(s) ;
 		  }
 		  stack.push(s,e.charAt(i)) ;	  
 	  }
   }while (!stack.isEmpty(s)) {
	   // pop any remaining operators from the stack and add them to the output string
  	  output += stack.getTop(s) ;
  	  stack.pop(s) ;
    }
		return output ;
	}
 ////////////////////////////////////////////////////////////////////////////////
	public static double evaluation(String eq) //Postfix Expression
	{		
		 CursorStack sa=new CursorStack();
		int stack = sa.alloc();	
		double n1;
		double n2;
		double result=0;
		
		for (int i=0 ; i<eq.length();i++)
		{
			 if (eq.charAt(i) == 32 ) {  // If the character is a space, skip to the next character
		 		  continue ;
		 	  }
			if (Character.isDigit(eq.charAt(i))) //If the character is a digit, push it onto the stack

			{
				sa.push(stack,eq.charAt(i));
			}
			else {
				// If the character is an operator,
	            // pop the two most recent operands from the stack,
	            // perform the operation, and push the result back onto the stack
				n1=Double.parseDouble(sa.getTop(stack).toString());
				sa.pop(stack);
				n2=Double.parseDouble(sa.getTop(stack).toString());
				sa.pop(stack);
				if (eq.charAt(i)=='+')
				{
				result =n2+n1;	
				sa.push(stack,result);
				}
				else if (eq.charAt(i)=='-')
				{
				result =n2-n1;
				sa.push(stack,result);
				}
				else if (eq.charAt(i)=='*')
				{
				result =n2*n1;
				sa.push(stack,result);
				}
				else if (eq.charAt(i)=='/')
				{
				result =n2/n1;
				try {
				if (n1!=0) {
				sa.push(stack,result);
				}
				} catch (ArithmeticException e) {
			         System.out.println ("Can't be divided by Zero " + e);
			         
			      }				
				}	
			}
		}
		return Double.parseDouble(sa.getTop(stack).toString());// return the last value in stack, which is the result of the calculation	 
	}	
	/////////////////////////////////////////////////////////////////////////////////
	public static boolean balanced (String s ) {
		int balanced = CursorStack.alloc() ; // Create a stack for storing the parentheses

		for (int i = 0 ; i < s.length() ; i++) {
			if (s.charAt(i) == '(') {
				CursorStack.push(balanced, s.charAt(i)) ;
			}
			else if (s.charAt(i) == ')') {
				if  (CursorStack.isEmpty(balanced) || (char)CursorStack.getTop(balanced) != '(') {
					return false; 
				}
				else {
					CursorStack.pop(balanced);
				}
			}
		}
		return CursorStack.isEmpty(balanced);// return true if the stack is empty
	}
	//////////////////////////////////////////////////////////////////
	public static boolean isValid(String expression) {
        try {
            // Try to evaluate the expression
            double result = evaluation(expression);
            // If the expression is valid, it will be evaluated successfully
            // and the result will be returned
            return true;
        } catch (Exception e) {
            // If an error occurs during the evaluation, the expression is not valid
            return false;
        }
    }
	////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
	   public static String getEquations(File file) {
	        String st=readfromfile(file);
	        String equations = "";
	        // Find the start and end indices of the 'equations' element
	        int eqStart = st.indexOf("<equations>");
	        int equEnd = st.indexOf("</equations>");
	        if (eqStart ==-1 && equEnd==-1)
	        {
	        	String equation = "no equations in this file";
       		    equations += equation + "\n";
	        }
	        if (eqStart != -1 && equEnd != -1) {
	            //  the contents of the 'equations' element
	            String equValue = st.substring(eqStart, equEnd + "</equations>".length());
	            // Find the start and end indices of each 'equation' element
	            int StartIndex = equValue.indexOf("<equation>");	
	            if  (StartIndex == -1 || equEnd== -1 ||eqStart == -1 || equEnd== -1)
		        {
		        	String equation = "missing tags";
	       		    equations += equation + "\n";
		        }
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

	////////////////////////////////////////////////////
	public static String getValues(File file)  {	
		String st=getEquations(file); 
	    String[] lines = st.split("\n");
	    String re = "";
	    for (int i = 0; i < lines.length; i++) {
	        String value = Methods.infixToPostfix(lines[i]);
	        if (Methods.balanced(value) && Methods.isValid(value)) {
	            re += (lines[i] + " >>>> " + value +
	                   " >>>> " + Methods.evaluation(value) + "\n");
	        } else if (Methods.balanced(value) == false) {
	            re += (lines[i] + ">>> unbalanced\n");
	        } else if (Methods.isValid(value) == false) {
	            re += (lines[i] + ">>> invalid\n");
	        }
	        else {
	        	re+="";
	        }
	    }
	    return re;
	}
////////////////////////////////////////////////////////////////////////////
	public static String getFiles(File fi) {
	    String st=readfromfile(fi);
	    String files = ""; 
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
	//////////////////////////////////////////////////////
	 public static String findFile(String str)
	    {
	    	 String fileName = str;
	         File root = new File("C:\\");  // Start the search in the root directory

	         Queue queue = new Queue();  // Create a queue to store the directories to search
	         queue.enqueue(root);  // Add the root directory to the queue

	         while (!queue.isEmpty()) {  // While there are still directories to search
	             File dir = queue.dequeue();  // Get the next directory
	             // it dequeues a directory from the queue and gets a 
	             //list of files in that directory
	             File[] files = dir.listFiles(); 
	            

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
	         return null;//not found
	    }
////////////////////////////////////////////////////////////////////////////////////////////
}
