import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RadioList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<RadioStation> radioListAM, radioListFM;

	public RadioList(){
		this.radioListAM = new ArrayList<RadioStation>();
		this.radioListFM = new ArrayList<RadioStation>();
		importRadioFromFile();
	}
	
	@SuppressWarnings("unchecked")
	public void importRadioFromFile(){
		
		//Import from file
		try{
			FileInputStream fileInAM = new FileInputStream("radiolistAM.ser");
			ObjectInputStream inAM = new ObjectInputStream(fileInAM);
			FileInputStream fileInFM = new FileInputStream("radiolistFM.ser");
			ObjectInputStream inFM = new ObjectInputStream(fileInFM);
			
			this.radioListAM = (ArrayList<RadioStation>) inAM.readObject();
			this.radioListFM = (ArrayList<RadioStation>) inFM.readObject();
	
			inAM.close();
			fileInAM.close();
			inFM.close();
			fileInFM.close();
		}catch (FileNotFoundException fileNotFound){
			File fileAM = new File("radiolistAM.ser");
			File fileFM = new File("radiolistFM.ser");
			try {
				fileAM.createNewFile();
				fileFM.createNewFile();
				fillDefaultRadioStation();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void exportRadioToFile(){
		try{
			FileOutputStream fileOutAM = new FileOutputStream("radiolistAM.ser");
			ObjectOutputStream outAM = new ObjectOutputStream(fileOutAM);
			FileOutputStream fileOutFM = new FileOutputStream("radiolistFM.ser");
			ObjectOutputStream outFM = new ObjectOutputStream(fileOutFM);
			outAM.writeObject(this.radioListAM);
			outAM.close();
			fileOutAM.close();
			outFM.writeObject(this.radioListFM);
			outFM.close();
			fileOutFM.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void fillDefaultRadioStation(){
		RadioStation station1 = new RadioStation("930", "AM", "Phoenix", "Phoenix Radio, Inc.");
		RadioStation station2 = new RadioStation("1050", "AM", "Phoenix", "Family Life Broadcasting.");
		RadioStation station3 = new RadioStation("1000", "AM", "Glendale", "Entravision Holdings.");
		RadioStation station4 = new RadioStation("1060", "AM", "Glendale", "Glendale FCC License Sub.");
		RadioStation station5 = new RadioStation("1058", "AM", "Scottsdale", "Gabrielle Broadcasting Licensee.");
		RadioStation station6 = new RadioStation("850", "AM", "Tempe", "Tempe FCC License Sub");
		
		radioListAM.add(station1);
		radioListAM.add(station2);
		radioListAM.add(station3);
		radioListAM.add(station4);
		radioListAM.add(station5);
		radioListAM.add(station6);
		
		RadioStation station7 = new RadioStation("89.5", "FM", "Phoenix", "Classical Music");
		RadioStation station8 = new RadioStation("91.5", "FM", "Scottsdale", "KJZZ");
		RadioStation station9 = new RadioStation("92.3", "FM", "Glendale", "News Talk");
		RadioStation station10 = new RadioStation("94.5", "FM", "Scottsdale", "Scottsdale Talk Radio");
		RadioStation station11 = new RadioStation("98.7", "FM", "Tempe", "Hard Rock Radio");
		RadioStation station12 = new RadioStation("99.1", "FM", "Tempe", "Radio Disney");
		
		radioListFM.add(station7);
		radioListFM.add(station8);
		radioListFM.add(station9);
		radioListFM.add(station10);
		radioListFM.add(station11);
		radioListFM.add(station12);
		
		exportRadioToFile();
	}
	
	public String[] radioAMAsString(){
		String[] strRadioAM = new String[radioListAM.size()];
		
		for (int i = 0; i < strRadioAM.length; i++) {
			strRadioAM[i] = radioListAM.get(i).getRadioAsString();
			//System.out.println(strContacts[i]);
		}
		return strRadioAM;
	}
	
	public String[] radioFMAsString(){
		String[] strRadioFM = new String[radioListFM.size()];
		
		for (int i = 0; i < strRadioFM.length; i++) {
			strRadioFM[i] = radioListFM.get(i).getRadioAsString();
			//System.out.println(strContacts[i]);
		}
		return strRadioFM;
	}
	
	public String getLocation(int j, String band) {
		int i = 0;
		if (band.equals("AM")) {
			for (RadioStation station: radioListAM) {
				if (i == j) return station.getCity();
				i++;
			}
		} else {
			for (RadioStation station: radioListFM) {
				if (i == j) return station.getCity();
				i++;
			}
		}
		return "None";
	}
}