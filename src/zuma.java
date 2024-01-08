/**
public void handleCollisions(LinkedList<Ball> movingBalls,LinkedList<Ball> projectiles) {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
        Ball projectile = projectiles.get(i); // Projektile
        // for (int j = movingBalls.size() - 1; j >= 0; j--) {
        for (int j = 0; j < movingBalls.size(); j++) {
        Ball ball = movingBalls.get(j); // Une Balle de la liste

        if (ball.collidesWith(projectile)) {

        // Trouver la position d'insertion correcte
        System.out.println("Colision " + ball.getType() + ": (x,y) =" + "(" + ball.getPosition().getX() + "," + ball.getPosition().getY() + ")" + " \n" + " mit Pokemon "
        + projectile.getType() + ": (x,y) =" + "(" + ball.getPosition().getX() + "," + ball.getPosition().getY() + ")");

/*

System.out.println("Projektile");
System.out.println(projectile);

                        projectiles.remove(projectile);
                        System.out.println(movingBalls);
                        System.out.println(movingBalls.size());
                        System.out.println(projectiles);
                        System.out.println(projectiles.size());


                        System.out.println(movingBalls);
                        */
/**
        System.out.println(movingBalls);
// hier zähle ich wieviele Objekte ähnlich zu dem Projektil an dieser Stelle vorkommen
        int sameTypeStart = j;
        int sameTypeEnd = j;
        /*
         * ici je determine le nombre de balles de meme type a la postion de la colission
         * */
/**
        //    int type = movingBalls.get(j).getType();
        int type = ball.getType();
        if (projectile.getType() == type) {
        movingBalls.add(j, projectile); // Da füge ich zuerst das Projektil
        while (sameTypeStart > 0 && movingBalls.get(sameTypeStart - 1).getType() == type) {
        sameTypeStart--; // nach links zählen
        System.out.println(j);
        System.out.println("nombre de balles similaires a gauche " + sameTypeStart);
        }

        while (sameTypeEnd < movingBalls.size() - 1 && movingBalls.get(sameTypeEnd + 1).getType() == type) {
        sameTypeEnd++; // nach recht  zählen
        System.out.println(j);
        System.out.println("nombre de balles similaires a droite " + sameTypeEnd);
        }

        if (sameTypeEnd - sameTypeStart >= 2) { // Wenn es mehr als drei insgesamt sind , entferne ich sie
        //        float gapDistance = (sameTypeEnd - sameTypeStart + 1) * pokemonSize + 25;

        for (int a = sameTypeEnd; a >= sameTypeStart; a--) {
        movingBalls.remove(a);
        }
        }
        } else if (projectile.getType() != ball.getType()) { // Si les types sont plutot differents j'ajoute la la postion de colision

        // Créez une nouvelle balle avec les propriétés du projectile
        //   Ball newBall = new Ball(projectile.position.x, projectile.position.y, projectile.type,ballSize,ball.velocity);
        //     System.out.println(movingBalls);
        //   projectile.setIprojectile(true);
        // Ajoutez la nouvelle balle à la liste
        //      movingBalls.add(j+1, projectile);
        //      System.out.println(movingBalls);

        /*
         * Je cree une nouvelle balle avec les attributs du projectil et je l'ajoute a la liste
         */


/**
        Ball  previewsBall = new Ball(ball.getPosition().getX()+ballSize, ball.getPosition().getY(),ball.getVelocity(), projectile.getType(),ballSize,false,ballStepDown,2,600,800);
        previewsBall.setUrlImage(projectil.getUrlImage());
        int k =0; // L#objectif ici est de decaler les balles vers l'avant a partir de la postion de colision
        while (k<movingBalls.size()) {
        if(k>j+1) {
        movingBalls.get(k).getPosition().setX(movingBalls.get(k).getPosition().getX() + ballSize);
        movingBalls.get(k).setUrlImage(movingBalls.get(k).getUrlImage());
        }else {
        movingBalls.get(k).getPosition().setX(movingBalls.get(k).getPosition().getX() );
        movingBalls.get(k).setUrlImage(movingBalls.get(k).getUrlImage());
        }
        k++;

        }

        movingBalls.add(j+1,previewsBall);
        projectiles.remove(projectile); // Retirer apres la collision
        }

        System.out.println(projectiles);
        break; // Sortir de la boucle pour éviter de vérifier les collisions avec d'autres balles

        }

        //   }
        }
        }

        }
*/