import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ContactList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	
	public ContactList(){
		this.contactList = new ArrayList<Contact>();
		importContacts();
		
	}
	
	//function to test
	public void fillDefaultContacts(){
		
		Contact cont1 = new Contact("Moreira P", "(480) 360-8699", false);
		Contact cont2 = new Contact("William", "(360) 333-3333", true);	
		Contact cont3 = new Contact("Igor Guedes", "(333) 333-3333", true);	
		
		contactList.add(cont1);
		contactList.add(cont2);
		contactList.add(cont3);
		
		exportToFile();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void importContacts() {
		//Import from file
		try{
			FileInputStream fileIn = new FileInputStream("contacts.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			this.contactList = (ArrayList<Contact>)(in.readObject());
	
			in.close();
			fileIn.close();
		}catch (FileNotFoundException fileNotFound){
			File file = new File("contacts.ser");
			try {
				file.createNewFile();
				fillDefaultContacts();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		} finally {
			
		}
		
		
		
	}
	
	
	public void exportToFile(){
		try{
			//export contactList to file
			FileOutputStream fout = new FileOutputStream("contacts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(contactList);
			
			oos.close();
	
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void listContacts(){
		//list all the contacts
		for(Contact temp : contactList){
			System.out.println(temp.getContactAsString());
		}
	}
	
	
	public String[] contactsAsString(){
		String[] strContacts = new String[contactList.size() ];
		
		for (int i = 0; i < strContacts.length; i++) {
			strContacts[i] = contactList.get(i).getContactAsString();
			//System.out.println(strContacts[i]);
		}
		
		return strContacts;
	}
	
	public void updateList(){
		//update listOfContacts
	}
}