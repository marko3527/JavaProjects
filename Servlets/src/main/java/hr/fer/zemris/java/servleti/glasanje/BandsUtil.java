package hr.fer.zemris.java.servleti.glasanje;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Util for bands. It offers various methods for calculating the best band, 
 * return the list of band names, band ids, band links and so on.
 * 
 * @author Marko
 *
 */
public class BandsUtil {
	
	private List<String> bandsAttributes;
	
	private List<Band> bands = new ArrayList<>();
	
	
	/**
	 * Constructor. It takes the lines held in 'glasanje-definicija.txt' 
	 * and calls the method processAttributes() which will make a list
	 * of bands.
	 * 
	 * @param bandsAttributes
	 */
	public BandsUtil(List<String> bandsAttributes) {
		this.bandsAttributes = bandsAttributes;
		processAttributes();
	} 
	
	
	/**
	 * Method that reads the lines of the file 'glasanje-definicija.txt'
	 * and makes a list of bands held in that file.
	 */
	private void processAttributes() {
		for(String line : bandsAttributes) {
			String[] attributes = line.split("\\t");
			bands.add(new Band(attributes[1], attributes[0], attributes[2]));
		}
	}
	
	
	/**
	 * Method that writes votes to the file in next format:
	 * 'id of the band' + '\t' + 'number of votes for that band'
	 * 
	 * @param fileName String representation of the file that should be written in
	 * @throws IOException if there was problem with writing to that file
	 */
	public void writeVotes(String fileName) throws IOException {
		FileWriter writer = new FileWriter(fileName, false);
		for(Band band : bands) {
			writer.write(band.getId() + "\t" + band.getNumberOfVotes() + "\n");
		}
		writer.close();
	}
	
	
	/**
	 * Method that reads through the file with votes and 
	 * assigns the number of votes to band with id written in
	 * votes file.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void readVotes(String fileName) throws IOException {
		List<String> votes = Files.readAllLines(Paths.get(fileName));
		for(String vote : votes) {
			String[] attributes = vote.split("\\t");
			String id = attributes[0];
			for(Band band : bands) {
				if(band.getId().equals(id)) {
					band.setNumberOfVotes(Integer.parseInt(attributes[1]));
				}
			}
		}
	}
	
	
	/**
	 * Method that takes the id of band for which the user voted for
	 * and assigns the one vote to it.
	 * 
	 * @param id {@code String} if of the vote
	 */
	public void addVote(String id) {
		for(Band band : bands) {
			if(band.getId().equals(id)) {
				band.setNumberOfVotes(band.getNumberOfVotes() + 1);
			}
		}
	}
	
	
	/**
	 * Method that returns the list of bands that have the highest
	 * number of votes.
	 * 
	 * @return {@code List<Band>}
	 */
	public List<Band> bandsWithMostVotes() {
		int mostVotes = findLargestNumberOfVotes();
		List<Band> bandsWithHighestVote = new ArrayList<>();
		
		for(Band band : bands) {
			if(band.getNumberOfVotes() == mostVotes) {
				bandsWithHighestVote.add(band);
			}
		}
		
		return bandsWithHighestVote;
	}
	
	
	/**
	 * Method that finds the number that represents the
	 * highest number of votes in list of bands.
	 * 
	 * @return {@code int} highest number of votes.
	 */
	private int findLargestNumberOfVotes() {
		int mostVotes = bands.get(0).getNumberOfVotes();
		for(Band band : bands) {
			if(band.getNumberOfVotes() > mostVotes) {
				mostVotes = band.getNumberOfVotes();
			}
		}
		return mostVotes;
	}
	
	
	/**
	 * Getter. Returns the list of band ids for which
	 * the user can vote for.
	 * 
	 * @return {@code List<String>}
	 */
	public List<String> getIds() {
		List<String> ids = new ArrayList<>();
		for(Band band : bands) {
			ids.add(band.getId());
		}
		return ids;
	}
	
	
	/**
	 * Getter. Returns the list of band links for which 
	 * the user can vote for.
	 *
	 * @return {@code List<String>}
	 */
	public List<String> getLinks() {
		List<String> links = new ArrayList<>();
		for(Band band : bands) {
			links.add(band.getLink());
		}
		return links;
	}
	
	
	/**
	 * Getter. Returns the list of band names for which the 
	 * user can vote for.
	 * 
	 * @return {@code List<String>}
	 */
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		for(Band band : bands) {
			names.add(band.getName());
		}
		return names;
	}
	
	public List<String> returnResults() {
		List<String> results = new ArrayList<>();
		for(Band band : bands) {
			results.add("" + band.getNumberOfVotes());
		}
		return results;
	}
	
	public List<Band> getBands() {
		return bands;
	}
	

}
