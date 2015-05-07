/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;





/**
 *
 * @author Abanoub
 */
public class Huffman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        
                   
        String [] choices = {"Compress" , "Decompress"} ;
        int response = JOptionPane.showOptionDialog(
                               null                       // Center in window.
                             , "What Do you Want To Do ?!"       // Message
                             , "Huffman"                // Title in titlebar
                             , JOptionPane.YES_NO_OPTION  // Option type
                             , JOptionPane.PLAIN_MESSAGE  // messageType
                             , null                       // Icon (none)
                             , choices                    // Button text as above.
                             , "Compress"                    // Default button's label
                           );
        
        switch (response) {
   ///////////////////////////////////////////////////////////////////////////       
          case 0 : {  
      Map <Character,Integer> map = new HashMap<Character,Integer>();    
      
       
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("input.txt", "txt");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null) ;
        
        File file = chooser.getSelectedFile();
        
        
        String text = "";    
        try {
            text = new Scanner( file ).useDelimiter("\\Z").next();
        } 
        catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Couldn't Find The File ! ");
        }
        
        System.out.println( "original input : " + text);
        
        while (text.length() % 3 != 0) {
            text += text.charAt(text.length()-1) ;
        }
        
        System.out.println( "original input that can be divided by 3 : " + text);
        
        String textRequested = "" ; 
        int length = text.length() ; 
        textRequested += text.substring((length/3)*2) ;
        textRequested += text.substring((length/3), (length/3)*2) ; 
        textRequested += text.substring(0, (length/3)) ; 
        
        text = textRequested ;
      
        System.out.println( "original input sub3 sub2 sub1 : " + text);
        
      for(int i = 0; i < text.length(); i++){
         char c = text.charAt(i);
         Integer val = map.get(new Character(c));
         if(val != null){
           map.put(c, new Integer(val + 1));
         }
         else map.put(c,1);
      }
      
      Map <Character, Integer> sortedMap = sortByComparator(map);
      List list = new LinkedList(sortedMap.entrySet()) ;
      ArrayList <Character> chars = new ArrayList () ;
      ArrayList <Double> prob = new ArrayList () ;
      for (Iterator it = list.iterator(); it.hasNext();) {
          String temp = it.next().toString() ;
          chars.add(temp.charAt(0));
          String temp2 = "" ; 
          boolean eq = false ; 
          for ( int i = 0 ; i < temp.length(); i++) {
              if (temp.charAt(i) == '=') {
                  eq = true ; 
                  continue;
              }
              
              if (eq) {
                  temp2 += temp.charAt(i) ;
              }
          }
          prob.add((Double.parseDouble(temp2)/text.length()));
      }
      
      Collections.reverse(chars);
      Collections.reverse(prob);
      String result = buildCode (chars,prob) ;
      PrintStream ps = new PrintStream("D:\\dictionary.txt");
      String lines[] = result.split("\\r?\\n");
      for ( int i  = 0 ; i <lines.length ; i++) {
          ps.println(lines[i]);
      }
      String comp = "" ;
      for ( int i = 0 ; i < text.length() ; i++) {
           for ( int k  = 0 ; k <lines.length ; k++) {
          if (text.charAt(i) == lines[k].charAt(0)) {
              for (int j = 2 ; j <lines[k].length() ; j++) {
                  comp += lines[k].charAt(j) ;
              }
          }
      }
      }
       PrintStream ps2 = new PrintStream("D:\\compressed.txt");
       ps2.println(comp);
      break ;
          }
  ///////////////////////////////////////////////////////////////////////////                
               case 1 : {
                    
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("dictionary.txt", "txt");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null) ;
        
        File file = chooser.getSelectedFile();
        
        
        String dic = "";    
        try {
            dic = new Scanner( file ).useDelimiter("\\Z").next();
        } 
        catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Couldn't Find The File ! ");
        }
        
          JFileChooser chooser2 = new JFileChooser();
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("compressed.txt", "txt");
        chooser2.setFileFilter(filter2);
        chooser2.showOpenDialog(null) ;
        
        File file2 = chooser2.getSelectedFile();
        
        
        String comp = "";    
        try {
            comp = new Scanner( file2 ).useDelimiter("\\Z").next();
        } 
        catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Couldn't Find The File ! ");
        }
        
         String diclines[] = dic.split("\\r?\\n");
         String Prefix = ""; 
         String text = "";
        for (int i = 0 ; i < comp.length() ; i++) {
            Prefix += comp.charAt(i ); 
            for ( int j = 0 ; j < diclines.length ; j++) {
                if ( Prefix.equals(diclines[j].substring(2))) {
                    Prefix = "" ;
                    text += diclines[j].charAt(0) ;
                }
            }
        }
      
        PrintStream ps2 = new PrintStream("D:\\decompressed.txt");
        ps2.println(text);
        
        
        
        
               }
        }
        }
 ///////////////////////////////////////////////////////////////////////////   
    private static String buildCode (ArrayList chars , ArrayList prob) {
        String result = "" ; 
        int size = chars.size() ;
        ArrayList <String> charsStrings = new ArrayList () ;
        Character temp ; 
        String temp2 ;
        Double temp3; 
        
        for ( int i = 0 ; i < chars.size() ; i++) {
            temp = (Character) chars.get(i ); 
            charsStrings.add(String.valueOf(temp));
        }
        
      
        
         while (charsStrings.size() > 2) {
             
              for ( int i = 0 ; i<prob.size() ; i++) {
               System.out.println(charsStrings.get(i) + ":" + prob.get(i)); 
           }
           System.out.println();
           temp3 = (Double) prob.get(size-1) ;
           temp3 += (Double) prob.get(size-2) ;
           temp2 = charsStrings.get(size-2) ;
           temp2 += " " + charsStrings.get(size-1) ;
           
         
           prob.remove(size-1) ;
           prob.remove(size-2) ;
           charsStrings.remove(size-1) ;
           charsStrings.remove(size-2) ;
           int temp4 = prob.size();
           for ( int i = 0 ; i < temp4 ; i++) {
               if ( temp3 >= (Double) prob.get(i)) {
                   System.out.println("la2eet akbr") ;
                   prob.add(i, temp3);
                   charsStrings.add(i,temp2);
                   break;
               }
               
               
              else if (i == prob.size()-1) {
                   System.out.println("mal2etsh") ;
                   prob.add(temp3);
                   charsStrings.add(temp2);
               }
           }
           size--;
       }
         
          for ( int i = 0 ; i<prob.size() ; i++) {
               System.out.println(charsStrings.get(i) + ":" + prob.get(i)); 
           }
           System.out.println();
         
           
           String PrefixLeft = "1" , PrefixRight = "0";
           String right , left ;
           right = charsStrings.get(0) ;
           left = charsStrings.get(1) ;
         
           
          
           
           for ( int i = right.length()-1 ; i >= 0 ; i -=2) {
               if ( i == 0) {
                   result += right.charAt(i) +":"+ PrefixRight + "\n";
                   break ; 
               }
                if ( i - 2 == 0) {
                   result += right.charAt(i-2) +":"+ PrefixRight + "0" + "\n" ;
                   result += right.charAt(i) +":"+ PrefixRight + "1" + "\n";
                   break ;
               }
               result += right.charAt(i) +":"+ PrefixRight + "1" + "\n";
              
               PrefixRight += "0" ;
               
           }
           
           for ( int i = left.length()-1 ; i >= 0 ; i -=2) {
               if ( i == 0) {
                   result += left.charAt(i) +":"+ PrefixLeft + "\n";
                   break ; 
               }
               
                if ( i - 2 == 0) {
                   result += left.charAt(i-2) +":"+ PrefixLeft + "0" + "\n" ;
                   result += left.charAt(i) +":"+ PrefixLeft + "1" + "\n";
                   break ;
               }
               
               result += left.charAt(i) +":"+ PrefixLeft + "1" + "\n";
              
               PrefixLeft += "0" ;
               
           }
       
      
        return result ; 
    }
    
    ///////////////////////////////////////////////////////////////////////////    
    private static Map<Character, Integer> sortByComparator(Map<Character, Integer> map) {
 
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

  
}
