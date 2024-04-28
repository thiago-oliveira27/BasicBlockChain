package chain; 

import java.util.Date;
import chain.util.StringUtil;

public class Block {
	
	private String hash;
	private String previousHash; 
	private String data;
	private long timeStamp;
	private int nonce;
	
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash();
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		return StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		long start = System.currentTimeMillis();
		String target = StringUtil.getDifficultyString(difficulty); //Create a string with difficulty * "0" 
		
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		long end = System.currentTimeMillis();
		long finalTime = (end - start);
		System.out.println("Block Mined!!! : " + hash);
		System.out.println("Final time: " + finalTime + " milliseconds or " + (finalTime/1000.0) + " seconds\n");
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}		
}