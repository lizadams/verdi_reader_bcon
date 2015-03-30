/* This prototype was modified from an example file that was part of the netCDF package.
   Copyright 2006 University Corporation for Atmospheric Research/Unidata.
   See COPYRIGHT file for conditions of use.

   This is an example which reads a sample BCON file. 
   It is based off of the program pres_temp_4D_rd.java
   The pres_temp_4D_rd.java program uses a data file that is produced by the
   companion program pres_temp_4D_wr.java. It is intended to
   illustrate the use of the netCDF Java API.

   This example demonstrates the netCDF Java API.

   Full documentation of the netCDF Java API can be found at:
   http://www.unidata.ucar.edu/software/netcdf-java/
*/

import ucar.nc2.Variable;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.ma2.ArrayFloat;
import ucar.ma2.InvalidRangeException;

import java.io.IOException;

public class BCON_3D_rd {

  public static void main(String args[]) {


    // Open the file.
    String filename = "BCON_sample.nc";
    NetcdfFile dataFile = null;
    try {

      dataFile = NetcdfFile.open(filename, null);

      // Get the number of columns and number of rows from the Global Attributes.
      Attribute nCols = dataFile.findGlobalAttribute("NCOLS");
      if (nCols == null) {
        System.out.println("Cant find Attribute NCOLS");
      return;
      }else {
      System.out.println(nCols);
      System.out.println(nCols.getNumericValue());
      }

      Attribute nRows = dataFile.findGlobalAttribute("NROWS");
      
      if (nRows == null) {
        System.out.println("Cant find Variable longitude");
        return;
      }else {
      System.out.println(nRows);
      System.out.println(nCols.getNumericValue());
      }


      // Get the NO2 variable.
      Variable NO2Var = dataFile.findVariable("NO2");
      if (NO2Var == null) {
        System.out.println("Cant find Variable NO2");
        return;
      }

      int[] shape = NO2Var.getShape();
      int recLen = shape[0]; // number of times

      int[] origin = new int[3];
      shape[0] = 1; // only one rec per read

      // loop over the rec dimension
      for (int rec = 0; rec < recLen; rec++) {
        origin[0] = rec;  // read this index

        // read 2D array for that index
        ArrayFloat.D2 NO2Perim;

        NO2Perim = (ArrayFloat.D2) (NO2Var.read(origin, shape).reduce());

        System.out.println("Value of NO2Perim"); 
          System.out.println(NO2Perim);

      }

      // The file is closed no matter what by putting inside a try/catch block.
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;

    } catch (InvalidRangeException e) {
      e.printStackTrace();
      return;

    } finally {
      if (dataFile != null)
        try {
          dataFile.close();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
    }
    System.out.println("*** SUCCESS reading example file " + filename);
  }

}
