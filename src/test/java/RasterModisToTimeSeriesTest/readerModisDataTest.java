package RasterModisToTimeSeriesTest;

import java.util.HashMap;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.jgrasstools.gears.io.shapefile.OmsShapefileFeatureReader;
import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorReader;
import org.jgrasstools.gears.io.timedependent.OmsTimeSeriesIteratorWriter;
import org.junit.Assert;
import org.junit.Test;
import RasterModisToTimeSeries.readerModisData;

public class readerModisDataTest {

	@Test
	public void test() throws Exception {


		OmsTimeSeriesIteratorReader reader = new OmsTimeSeriesIteratorReader();
		reader.file ="resources/input/timeseries.csv";
		reader.idfield = "ID";
		reader.tStart = "2007-05-25 00:00";
		reader.tTimestep = 60;
		reader.tEnd = "2007-07-15 23:00";
		reader.fileNovalue = "-9999";

		reader.initProcess();

		readerModisData modisReader= new readerModisData();

		modisReader.inFolder="/home/drugo/git/RasterModisToTimeSeries/resources/input/";// /Users/marialaura/Dropbox/dati_NewAge/EsercitazioniIdrologia2017/data/Basilicata/lai";
		
		OmsShapefileFeatureReader stationsReader = new OmsShapefileFeatureReader();
		stationsReader.file = "/home/drugo/git/RasterModisToTimeSeries/resources/input/centroidi_Trentino.shp";		
		stationsReader.readFeatureCollection();
		SimpleFeatureCollection stationsFC = stationsReader.geodata;


		OmsTimeSeriesIteratorWriter writer = new OmsTimeSeriesIteratorWriter();
		writer.file = "resources/output/LAI_prova.csv ";
		writer.tStart = reader.tStart;
		writer.tTimestep = reader.tTimestep;
		


		while( reader.doProcess ) {
			reader.nextRecord();
			modisReader.tCurrent=reader.tCurrent;
			modisReader.inStations = stationsFC;
			modisReader.fStationsid = "ID";
			modisReader.dataType="tif";
			modisReader.scaleFactor=0.1;
			modisReader.prj="/home/drugo/git/RasterModisToTimeSeries/resources/input/SystemRif.prj";

			modisReader.process();

			HashMap<Integer, double[]> resultD = modisReader.outValueHM;



			writer.inData = resultD;
			writer.writeNextLine();

		}
		//
		reader.close();
		writer.close();


	}


}
