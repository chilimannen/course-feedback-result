import Model.AsyncVotingStore;
import Model.Option;
import Model.Serializer;
import Model.VotingResult;
import io.vertx.core.Future;

import java.util.ArrayList;

/**
 * @author Robin Duda
 *         <p/>
 *         Mock for the voting database.
 */
public class VotingDBMock implements AsyncVotingStore {
    private ArrayList<VotingResult> votes = new ArrayList<>();

    public VotingDBMock() {
        votes.add((VotingResult) Serializer.unpack(APITest.getVotingConfiguration(), VotingResult.class));
    }


    @Override
    public void results(Future<Void> future, VotingResult result) {
        votes.add(result);
        future.complete();
    }

    @Override
    public void download(Future<VotingResult> future, String id) {
        future.complete(votes.get(0));
    }

    @Override
    public void view(Future<VotingResult> future, String id) {
        future.complete(votes.get(0));
    }
}
