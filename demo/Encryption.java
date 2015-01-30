//Written by: Andrew Lavelle

package demo;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;



public class Encryption {

    public String SHA1(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException 

                {

                    MessageDigest md=null;

                    md = MessageDigest.getInstance("SHA-1");//assigns sha-1 encoding to digest

                    //Alternatively md5 can be entered for a different type of hash

                    md.reset();//clears md before adding new string to it so as to replace any current string there

                    byte[] sha1hash = new byte[40];//creates a new byte object to store the digest

                    md.update(password.getBytes("UTF-8"), 0, password.length());//md.update inputs the text in utf-8 bit format

                    sha1hash = md.digest();

                    //System.out.println(""+sha1hash.toString());

                    return convertToHex(sha1hash);//it returns the the sha1 hash in hexadecimal format here

                

                }

    
   private String convertToHex(byte[] data) 

    {

         StringBuffer password = new StringBuffer();

         for (int i = 0; i < data.length; i++) 

         {

                 int hexnum = (data[i] >>> 4) & 0x0F;//0x0f is a 16bit representation ie 0-15

                 //you could use modulus 16 instead but this is more efficient



                 int two_halfs = 0;

                 do {

                     if ((0 <= hexnum) && (hexnum <= 9))/*finds all hexadecimal numbers between
                        0 and 9 and appends them normally*/

                         password.append((char) ('0' + hexnum));

                     else

                             password.append((char) ('a' + (hexnum - 10)));/*finds all hexadecimal numbers between
               0 and 9 and appends them with letters instead of numbers 10,11,12,...,16*/

                     hexnum = data[i] & 0x0F;

                    } while(two_halfs++ < 1);

         }

         return password.toString();//returns the password as a string again

     }

  

}

