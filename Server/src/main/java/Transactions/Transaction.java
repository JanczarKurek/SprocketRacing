package Transactions;

import ErrorsAndExceptions.TransactionFailure;

import java.util.ArrayList;

/** Oh boi
 *  represents list of changes that must occur in an atomic manner - all/none.
 *  One can register his own action and validate whole set of actions.
 *
 */

public class Transaction {
    private ArrayList<Runnable> actions = new ArrayList<>();
    void registerAction(Runnable runnable){
        actions.add(runnable);
    }
    private boolean cancelled = false;
    private boolean validated = false;
    void validate() throws TransactionFailure{
        if(validated)
            throw new TransactionFailure();
        if(!cancelled) {
            validated = true;
            for (Runnable action : actions)
                action.run();
        }
        else
            throw new TransactionFailure();
    }
    void cancel() throws TransactionFailure {
        if(validated)
            throw new TransactionFailure();
        if(cancelled)
            throw new TransactionFailure();
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isValidated(){
        return validated;
    }
}
