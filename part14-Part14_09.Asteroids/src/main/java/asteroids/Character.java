/*
 * Copyright (C) 2020 Dantes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package asteroids;

/**
 *
 * @author Dantes
 */
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {

    private Polygon character;
    private Point2D movement;
    

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }

 public void move() {
    this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
    this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

    if (this.character.getTranslateX() < 0) {
        this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.WIDTH);
    }

    if (this.character.getTranslateX() > AsteroidsApplication.WIDTH) {
        this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.WIDTH);
    }

    if (this.character.getTranslateY() < 0) {
        this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.HEIGHT);
    }

    if (this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
        this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.HEIGHT);
    }
}

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }
 
 

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.01;
        changeY *= 0.01;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    public boolean collide(Character other) {
    Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
    return collisionArea.getBoundsInLocal().getWidth() != -1;
}
}