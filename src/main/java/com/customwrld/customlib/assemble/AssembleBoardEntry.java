package com.customwrld.customlib.assemble;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;

public class AssembleBoardEntry {

	private final AssembleBoard board;
	@Setter private Component component;
	@Getter private String identifier;
	private Team team;
	private int position;

	/**
	 * Assemble Board Entry
	 *
	 * @param board that entry belongs to.
	 * @param component of entry.
	 * @param position of entry.
	 */
	public AssembleBoardEntry(AssembleBoard board, Component component, int position) {
		this.board = board;
		this.component = component;
		this.position = position;
		this.identifier = this.board.getUniqueIdentifier(position);

		this.setup();
	}

	/**
	 * Setup Board Entry.
	 */
	public void setup() {
		final Scoreboard scoreboard = this.board.getScoreboard();

		if (scoreboard == null) {
			return;
		}

		String teamName = this.identifier;

		// This shouldn't happen, but just in case.
		if (teamName.length() > 16) {
			teamName = teamName.substring(0, 16);
		}

		Team team = scoreboard.getTeam(teamName);

		// Register the team if it does not exist.
		if (team == null) {
			team = scoreboard.registerNewTeam(teamName);
		}

		// Add the entry to the team.
		if (team.getEntries().isEmpty() || !team.getEntries().contains(this.identifier)) {
			team.addEntry(this.identifier);
		}

		// Add the entry if it does not exist.
		if (!this.board.getEntries().contains(this)) {
			this.board.getEntries().add(this);
		}

		this.team = team;
	}

	/**
	 * Send Board Entry Update.
	 *
	 * @param position of entry.
	 */
	public void send(int position) {
		this.team.prefix(this.component);

		Score score = this.board.getObjective().getScore(this.identifier);
		score.setScore(position);
	}

	/**
	 * Remove Board Entry from Board.
	 */
	public void remove() {
		this.board.getIdentifiers().remove(this.identifier);
		this.board.getScoreboard().resetScores(this.identifier);
	}

}
