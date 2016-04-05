package Controller;

import Configuration.Config;
import Model.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Robin Duda
 *         <p/>
 *         REST API Routes for creating new votes on this master,
 *         and to receive and aggregate votes from multiple clients.
 */
public class APIRouter {
    private TokenFactory serverToken = new TokenFactory(Config.SERVER_SECRET);
    private AsyncVotingStore votings;

    public APIRouter(Router router, AsyncVotingStore votings) {
        this.votings = votings;

        router.get("/api/download/:id").handler(this::download);
        router.get("/api/view/:id").handler(this::view);
        router.post("/api/results").handler(this::results);
    }

    private void download(RoutingContext context) {
        HttpServerResponse response = context.response();
        Future<VotingResult> future = Future.future();

        future.setHandler(result -> {
            if (result.succeeded()) {
                response.putHeader("Content-Type", "application/octet-stream");
                response.putHeader("Content-Disposition", "attachment; filename=" + result.result().getTopic() + ".json");
                response.end(Serializer.json(result.result()).encodePrettily());
            } else
                response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
        });

        votings.download(future, context.request().getParam("id"));
    }

    private void view(RoutingContext context) {
        HttpServerResponse response = context.response();
        Future<VotingResult> future = Future.future();

        future.setHandler(result -> {
            if (result.succeeded()) {
                response.end(Serializer.pack(result.result()));
            } else
                response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
        });

        votings.view(future, context.request().getParam("id"));
    }


    private void results(RoutingContext context) {
        HttpServerResponse response = context.response();

        if (authorized(context)) {
            VotingResult result = (VotingResult) Serializer.unpack(context.getBodyAsJson().getJsonObject("voting"), VotingResult.class);
            Future<Void> future = Future.future();

            future.setHandler(storage -> {
                if (storage.succeeded())
                    response.setStatusCode(HttpResponseStatus.OK.code()).end();
                else
                    response.setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
            });

            votings.results(future, result);
        }
    }


    private boolean authorized(RoutingContext context) {
        Token token = (Token) Serializer.unpack(context.getBodyAsJson().getJsonObject("token"), Token.class);
        boolean authorized = serverToken.verifyToken(token);

        if (!authorized)
            context.response().setStatusCode(HttpResponseStatus.UNAUTHORIZED.code()).end();

        return authorized;
    }
}
