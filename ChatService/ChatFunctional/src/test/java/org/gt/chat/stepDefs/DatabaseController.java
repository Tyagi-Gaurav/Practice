package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.gt.chat.scenario.ScenarioConfig;

import java.io.IOException;

import static org.gt.chat.scenario.ConfigVariables.DATABASE_HOST;
import static org.gt.chat.scenario.ConfigVariables.DATABASE_PORT;

@Singleton
public class DatabaseController implements Controller {
    private MongodExecutable mongodExecutable;
    private ScenarioConfig scenarioConfig;

    @Inject
    public DatabaseController(ScenarioConfig scenarioConfig) {
        this.scenarioConfig = scenarioConfig;
    }

    @Override
    public void start() {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        String bindIp = scenarioConfig.getString(DATABASE_HOST);
        int port = scenarioConfig.getInt(DATABASE_PORT);;

        try {
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                    .build();

            mongodExecutable = null;

            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws RuntimeException {
        if (mongodExecutable != null)
            mongodExecutable.stop();
    }
}
