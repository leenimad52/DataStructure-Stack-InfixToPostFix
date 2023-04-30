package application;	
import java.io.File;


import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.lang.Runtime;
public class Main extends Application {
	//Methods methods =new Methods();
	//static Stack stack=new Stack();
   static int stackfileLabel=CursorStack.alloc();
   static int equStack=CursorStack.alloc();
   static int fileStack=CursorStack.alloc();

	@Override
	public void start(Stage primaryStage)throws Exception {
		try {
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(20));
			Label welc=new Label("Math equation solver");
			welc.setFont(new Font("Impact",20));
			welc.setTextFill(Color.TEAL);
			welc.setTranslateX(160);;
			Button back=new Button("Back");
			back.setPrefSize(50, 10);
			back.setStyle("-fx-background-color: #5F9EA0");
		    back.setFont(new Font("Times New Roman",13));
			Button load =new Button("Load file");
			load.setPrefSize(70,10);
			load.setStyle("-fx-background-color: #5F9EA0");
		    load.setFont(new Font("Times New Roman",13));
			 TextField viewfile=new TextField();
			// viewfile.setStyle("-fx-background-color: #F0FFFF");
			 FileChooser fileChooser = new FileChooser();
			 viewfile.setPrefSize(200, 20);
			 viewfile.setEditable(false);		         
		  //////////////////////////////////////////////////////////////
		   HBox hb=new HBox(10,back,viewfile,load);
		   hb.setAlignment(Pos.CENTER);      
		   TextArea equv=new TextArea();
		   Label equ=new Label("Eqauations");
		   equ.setFont(new Font("Times New Roman",15));
		   equ.setTextFill(Color.DARKCYAN);
		   equv.setPrefSize(80, 300);
		   equv.setEditable(false);
		   equv.setStyle("-fx-background-color: #F0FFFF");
		   Label fil=new Label("Files");
		   fil.setFont(new Font("Times New Roman",15));
		   fil.setTextFill(Color.DARKCYAN);
		   root.setStyle("-fx-background-color: #FDF5E6");
 		   ListView<String> listView1 = new ListView<>();
			//////////////////////////////////////////////////	   
		   load.setOnAction(e -> {
	            File selectedFile = fileChooser.showOpenDialog(primaryStage);
	            if (selectedFile != null) {
	                String fileAsString = selectedFile.toString();
		            viewfile.setText(fileAsString);
		            Methods.readfromfile(selectedFile);  
		            equv.setText(Methods.getValues(selectedFile));
		            String st=Methods.getFiles(selectedFile);
		            String[] lines = st.split("\n");  
		            ObservableList<String> items = FXCollections.observableArrayList();
		            CursorStack.push(stackfileLabel, selectedFile);
		            //CursorStack.push(fileStack, Methods.getFiles(selectedFile));
		            CursorStack.push(equStack, Methods.getValues(selectedFile));          
		            CursorStack.push(fileStack,items);	
		         for (String line : lines) {
			         items.add(line);
		        	    System.out.println("old list: "+line);
		        	}
		            listView1.setItems(items);
                    //////////////////
		        	listView1.setOnMouseClicked(event -> {
		        	    int index = listView1.getSelectionModel().getSelectedIndex();
		        	    String clickeditem = listView1.getItems().get(index).trim();
		        	    File clickedfile=new File(Methods.findFile(clickeditem));		        	    		        	  
		        	    if (clickedfile != null)
		        	    {
		        	      String st2=Methods.getFiles(clickedfile);
		  		          String[] lines2 = st2.split("\n");  
		  		          ObservableList<String> newItems = FXCollections.observableArrayList();
		  		          for (String line2 : lines2) {
		  			         newItems.add(line2);	
		  			         System.out.println("new list "+line2);
		  		        	}
		  				    listView1.setItems(newItems);
				            equv.setText(Methods.getValues(clickedfile));
				            viewfile.setText(clickedfile.toString());
				            CursorStack.push(stackfileLabel, clickedfile.toString());
				            CursorStack.push(equStack,  Methods.getValues(clickedfile));
				        //    CursorStack.push(fileStack,  Methods.getFiles(clickedfile));
				            CursorStack.push(fileStack,newItems);				            
		        	    }      	    
		        	});		        	
                   }           
	        });
	          /////////////////////////
		    back.setOnAction(e->{
	        	  try {
	        	  if (CursorStack.isEmpty(stackfileLabel)==false
	        			 // &&CursorStack.isEmpty(fileStack)==false&&
	        			  && CursorStack.isEmpty(equStack)==false) {  		  
	        	   CursorStack.pop(stackfileLabel);
	        	//   CursorStack.pop(fileStack);
	        	   CursorStack.pop(equStack);
	        	   CursorStack.pop(fileStack);
	        	   
	        	   if(CursorStack.getTop(stackfileLabel)!=null &&
	        			//   CursorStack.getTop(fileStack)!=null &&
	        			   CursorStack.getTop(equStack)!=null &&
	        			   CursorStack.getTop(fileStack)!=null ) {  		   
	        		   ObservableList<String> previtems = FXCollections.observableArrayList();
	        		   String prevFiles=  CursorStack.getTop(fileStack).toString().replace("[", "").replace("]", "");
	        		   String[] elements =  prevFiles.split(",");
	        		   for (String element : elements) {
	        			   previtems.add(element.trim());
	        			    System.out.println("prev files: "+element.trim());
	        			}                        		    
		  		   listView1.setItems(previtems);
	        	   viewfile.setText(CursorStack.getTop(stackfileLabel).toString());
	       	       equv.setText(CursorStack.getTop(equStack).toString());
	        	   }	   
	        	   else {
		        	   viewfile.setText("");
		        	   equv.setText("");
		        	   listView1.getItems().clear();
	        	   }
	        	  }
	        	   } catch(Exception e2)
	        	   {
		        	        System.out.println(e2.getMessage());
	        	   }
	           });
		    
		   // listView1.setStyle("-fx-background-color: #FFFAF0");
		    listView1.setPrefSize(50, 250);
		 //   viewfile.setStyle("-fx-background-color: #F8F8FF");
			   VBox vb=new VBox(5,welc,hb,equ,equv,fil,listView1);
			   root.setCenter(vb);
			Scene scene = new Scene(root,550,450);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) {
		launch(args);
	}
	////////////////////////////////////
}
