package chain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class BlockMain {

	private static final int DIFFICULTY = 1;
	private static final int NUM_BLOCKS = 20;

	public static ArrayList<Block> blockchain = new ArrayList<>();

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

//		Adding the first block without getting previous hash, 'cause its the first in the chain
		addBlock(new Block("Hi im the first block", "0"));
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(DIFFICULTY);

		int i;
		for (i = 1; i < NUM_BLOCKS; i++) {
			addBlock(new Block("BlockId-" + i, blockchain.get(blockchain.size() - 1).getHash()));
			System.out.println(String.format("Trying to Mine block %d... ", i + 1));
			blockchain.get(i).mineBlock(DIFFICULTY);
		}

		long end = System.currentTimeMillis();
		long finalTime = (end - start);
		System.out.println(String.format("Time for creating and mine the %d blocks: %d milliseconds or %.3f seconds", i, finalTime, (finalTime / 1000.0)));
		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[DIFFICULTY]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.getHash().substring(0, DIFFICULTY).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}

	public static void addBlock(Block newBlock) {
		blockchain.add(newBlock);
	}
}
