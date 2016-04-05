package Model;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * @author Robin Duda
 *         <p/>
 *         Async voting store implementation using MongoDB.
 */
public class VotingDB implements AsyncVotingStore {
    private static final String COLLECTION = "transmitter_results";
    private MongoClient client;

    public VotingDB(MongoClient client) {
        this.client = client;
    }


    @Override
    public void results(Future<Void> future, VotingResult result) {
        JsonObject insert = Serializer.json(result);

        client.insert(COLLECTION, insert, storage -> {
            if (storage.succeeded())
                future.complete();
            else
                future.fail(storage.cause());
        });

    }

    @Override
    public void download(Future<VotingResult> future, String id) {
        JsonObject query = new JsonObject().put("id", id);

        client.findOne(COLLECTION, query, null, storage -> {
            if (storage.succeeded())
                future.complete((VotingResult) Serializer.unpack(storage.result(), VotingResult.class));
            else
                future.fail(storage.cause());
        });
    }

    @Override
    public void view(Future<VotingResult> future, String id) {
        JsonObject query = new JsonObject().put("id", id);
        JsonObject filter = new JsonObject().put("options.values.keys", 0);

        client.findOne(COLLECTION, query, filter, storage -> {
            if (storage.succeeded() && storage.result() != null)
                future.complete((VotingResult) Serializer.unpack(storage.result(), VotingResult.class));
            else
                future.fail(storage.cause());
        });
    }
}
