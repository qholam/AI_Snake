/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tarsis
 */

public class FactoryAlgorithm {

    private Snake snake;

    public FactoryAlgorithm(Snake snake) {
        this.snake = snake;
    }
    
    public Algorithm getInstance(AlgorithmEnum algorithm){        
        switch(algorithm){
            case AI:
                return new AI(snake);            
        }
        
        throw new IllegalArgumentException("Algoritmo não disponivel!");
    }
    
}
