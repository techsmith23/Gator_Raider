package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.Attacker;
import game.models.Game;
import game.models.Node;

import java.util.List;

public final class StudentAttackerController implements AttackerController {

	public void init(Game game) {
	}

	public void shutdown(Game game) {

	}

	public int update(Game game, long timeDue) {
		int action = 0;
		Node NormalPill_location, PowerPill_location, gator_direction, enemy;

		Attacker gator_attacker = game.getAttacker();

		//Chooses a random LEGAL action if required.
		List<Integer> possibleDirs = gator_attacker.getPossibleDirs(true);

		for (int i = 0; i < possibleDirs.size() ; i++) {

			boolean Vulnerable = game.getDefender(i).isVulnerable();

			gator_direction = gator_attacker.getLocation();

			enemy = game.getDefender(i).getLocation();

			NormalPill_location = game.getPillList().get(i);

			if (!Vulnerable) {

				if (possibleDirs.size() != 0) {

					if (!game.getPowerPillList().isEmpty()) {

						PowerPill_location = game.getPowerPillList().get(i);

						action = gator_attacker.getNextDir(PowerPill_location, true);

						if(gator_direction.getPathDistance(PowerPill_location) == enemy.getPathDistance(PowerPill_location)){

							action = gator_attacker.getReverse();

						}

					}

					else if (game.getPowerPillList().isEmpty() && !game.getCurMaze().getPillNodes().isEmpty()){

						action = gator_attacker.getNextDir(NormalPill_location, true);

						if(gator_direction.getPathDistance(NormalPill_location) == enemy.getPathDistance(NormalPill_location)){

							action =  gator_attacker.getNextDir(NormalPill_location,false);

						}

					}


					return action;

				}

			}

			else if (Vulnerable) {

				action = gator_attacker.getNextDir(enemy,true);

				if (gator_direction.getPathDistance(NormalPill_location) == enemy.getPathDistance(NormalPill_location) ){

					action = gator_attacker.getReverse();
				}

				return action;

			}

			else {

				action = -1;

			}

		}

		return action;
	}


}


