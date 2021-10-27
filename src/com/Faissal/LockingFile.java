package com.Faissal;
import java.io.*;
import java.nio.channels.*;


public class LockingFile {
    public static final boolean EXCLUSIVE = false ;
    public static final boolean SHARED = true;


    public static void main(String[] args) throws IOException{
	FileLock sharedLock = null;
	FileLock exclusiveLock = null;
	try{
	    RandomAccessFile raf = new RandomAccessFile("test.txt" , "rw");
	    //get the channel from the file
        FileChannel ch =  raf.getChannel();
        //this locks the first half of the file - exclusive
        exclusiveLock = ch.lock(0 , raf.length()/2 , EXCLUSIVE);

        /**Now Moddify the data **/
        

        //release the lock
        exclusiveLock.release();
        //this locks the second half of the file - shared
        sharedLock = ch.lock(raf.length()/2+1 , raf.length() , SHARED );

        /**Now Read the Data **/


        //release the lock
        sharedLock.release();

    }
	catch(java.io.IOException ioe)
        {
            System.err.println(ioe);
        }
	finally{
	    if(exclusiveLock != null){
	        exclusiveLock.release();
        }
	    if(sharedLock != null){
	        sharedLock.release();
        }
    }
    }
}
