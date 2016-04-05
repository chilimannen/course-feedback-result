package Model;

import io.vertx.core.Future;

/**
 * @author Robin Duda
 */
public interface AsyncVotingStore {
    /**
     * Inserts a resultset into the datastore.
     * @param result the result to be inserted.
     */
    void results(Future<Void> future, VotingResult result);

    /**
     * Get the complete entry of specified votingresult.
     * @param id voting id specifier.
     */
    void download(Future<VotingResult> future, String id);

    /**
     * Get the metadata of a specified votingresult.
     * @param id voting id specified.
     */
    void view(Future<VotingResult> future, String id);
}
