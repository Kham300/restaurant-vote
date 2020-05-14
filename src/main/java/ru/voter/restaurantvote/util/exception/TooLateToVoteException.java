package ru.voter.restaurantvote.util.exception;

public class TooLateToVoteException extends RuntimeException {

    public TooLateToVoteException() {
        super("Too late to vote! Try tomorrow until 11:00 AM");
    }
}
