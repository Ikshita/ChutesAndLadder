package com.ikshita.chutesandladder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * 
 * @author Ikshita Mehta
 *
 */
public class PlayChutesAndLadder {

	HashMap<String, Integer> players = new HashMap<String, Integer>();
	HashMap<Integer, Integer> ladder = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> chute = new HashMap<Integer, Integer>();
	public final int WINNING_SQUARE = 100;
	public final int MAX_DICE_COUNT = 6;
	public final int MIN_DICE_COUNT = 1;
	public final int MAX_PLAYER_COUNT = 4;
	public final int MIN_PLAYER_COUNT = 2;

	/**
	 * All chutes and ladders are setup and all the players are setup when the object is created.
	 */
	public PlayChutesAndLadder() {
		
		setUpChutesAndLadder();
		setUpPlayers();
	}

	/**
	 * Sets the player name and default start square for all the players.
	 */
	private void setUpPlayers() {

		String[] nameOfPlayers = { "Eric", "Paul", "Joe", "Matte" };
		int noOfPlayers = spinTheSpinner(MAX_PLAYER_COUNT, MIN_PLAYER_COUNT);
		for (int i = 0; i < noOfPlayers; i++) {
			players.put(nameOfPlayers[i], 0);
		}

	}

	/**
	 * Sets all the new square positions for all the chutes and ladders.
	 */
	public void setUpChutesAndLadder() {

		ladder.put(1, 38);
		ladder.put(4, 14);
		ladder.put(9, 31);
		ladder.put(21, 42);
		ladder.put(28, 84);
		ladder.put(36, 44);
		ladder.put(51, 67);
		ladder.put(71, 91);
		ladder.put(80, 100);

		chute.put(16, 6);
		chute.put(47, 26);
		chute.put(49, 11);
		chute.put(56, 53);
		chute.put(62, 19);
		chute.put(64, 60);
		chute.put(87, 24);
		chute.put(93, 73);
		chute.put(95, 75);
		chute.put(98, 77);
	}

	/**
	 * It is the core method which controls the over all flow of the game
	 */
	public void start() {

		boolean playerWon = false;
		int iteration = 1;
		while (!playerWon) {
			for (Map.Entry<String, Integer> player : players.entrySet()) {
				StringBuffer output = new StringBuffer();
				output.append(iteration++);
				int spinnerValue = spinTheSpinner(MAX_DICE_COUNT, MIN_DICE_COUNT);
				movePawn(player, spinnerValue, output);
				playerWon = checkIfPlayerWon(player, output);
				if (playerWon) {
					System.out.println("The winner is " + player.getKey());					
					break;
				}
			}
		}
	}

	/**
	 * This method checks if the player has reached the winning square
	 * @param player
	 * @param output
	 * @return
	 */
	private boolean checkIfPlayerWon(Entry<String, Integer> player, StringBuffer output) {
		if (player.getValue() == WINNING_SQUARE)
			return true;
		else
			return false;
	}

	/**
	 * This method moves player to next square according to the rules of the game
	 * @param player
	 * @param spinnerValue
	 * @param output
	 */
	private void movePawn(Entry<String, Integer> player, int spinnerValue, StringBuffer output) {

		int originallyOnSquare = (int) player.getValue();
		int moveToSquare = originallyOnSquare + spinnerValue;
		
		output.append(": " + player.getKey() + ": " + originallyOnSquare + " --> ");
		
		//If calculated moveToSquare is greater then winning square then player will stay at the same square
		if (moveToSquare <= WINNING_SQUARE) {
			output.append(moveToSquare);
			if (ladder.containsKey(moveToSquare)) {
				moveToSquare = ladder.get(moveToSquare);
				output.append(" --LADDER--> " + moveToSquare);
			} else if (chute.containsKey(moveToSquare)) {
				moveToSquare = chute.get(moveToSquare);
				output.append(" --CHUTE--> " + moveToSquare);
			}
		} else {
			moveToSquare = originallyOnSquare;
			output.append(moveToSquare);
		}
		
		System.out.println(output);
		players.put((String) player.getKey(), moveToSquare);
	}

	/**
	 * This method will generate random values for number of player and dice
	 * @param maxCount
	 * @param minCount
	 * @return
	 */
	private int spinTheSpinner(int maxCount, int minCount) {
		return ((new Random()).nextInt(maxCount - minCount + 1) + minCount);
	}
}