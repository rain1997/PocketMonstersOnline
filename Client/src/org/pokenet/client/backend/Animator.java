package org.pokenet.client.backend;

import org.pokenet.client.backend.entity.Player;

public class Animator {
	private ClientMapMatrix m_mapMatrix;

	private static final int ANIMATION_INCREMENT = 4;

	// Sets up calls
	public Animator(ClientMapMatrix maps) {
		m_mapMatrix = maps;
	}

	// Prepares for animation
	public void animate() {
		try {
			ClientMap map = m_mapMatrix.getCurrentMap();
			if(map != null) {
				for(int i = 0; i < m_mapMatrix.getPlayers().size(); i++) {
					animatePlayer(m_mapMatrix.getPlayers().get(i));
				}
			}
		} catch (Exception e) {}
	}

	/**
	 * Animates players moving
	 * @param p
	 */
	private void animatePlayer(Player p) {
		/*
		 * Keep the screen following the player, i.e. move the map also
		 */
		if (p.isOurPlayer()) {
			if (p.getX() > p.getServerX()) {
				m_mapMatrix.getCurrentMap().setXOffset(
						(m_mapMatrix.getCurrentMap().getXOffset() + ANIMATION_INCREMENT),
						true);
			} else if (p.getX() < p.getServerX()) {
				m_mapMatrix.getCurrentMap().setXOffset(
						(m_mapMatrix.getCurrentMap().getXOffset() - ANIMATION_INCREMENT),
						true);
			} else if (p.getY() > p.getServerY()) {
				m_mapMatrix.getCurrentMap().setYOffset(
						(m_mapMatrix.getCurrentMap().getYOffset() + ANIMATION_INCREMENT),
						true);
			} else if (p.getY() < p.getServerY()) {
				m_mapMatrix.getCurrentMap().setYOffset(
						(m_mapMatrix.getCurrentMap().getYOffset() - ANIMATION_INCREMENT),
						true);
			}
		}
		/*
		 * Move the player
		 */
		if (p.getX() > p.getServerX()) {
			p.setX(p.getX() - ANIMATION_INCREMENT);
		} else if (p.getX() < p.getServerX()) {
			p.setX(p.getX() + ANIMATION_INCREMENT);
		} else if (p.getY() > p.getServerY()) {
			p.setY(p.getY() - ANIMATION_INCREMENT);
		} else if (p.getY() < p.getServerY()) {
			p.setY(p.getY() + ANIMATION_INCREMENT);
		}
		/*
		 * The player is now in sync with the server, stop moving/animating them
		 */
		if (p.getX() == p.getServerX() && p.getY() == p.getServerY() && p.isAnimating()) {
			p.setDirection(p.getDirection());
			p.setAnimating(false);
			p.loadSpriteImage();
		}
	}
}