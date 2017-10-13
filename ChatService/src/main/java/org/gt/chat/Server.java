package org.gt.chat;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.gt.chat.repos.ChatMessageRepository;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.resource.MessageResource;
import org.gt.chat.service.ChatMessageService;
import org.gt.chat.service.MessageService;

import java.io.IOException;
import java.net.URI;

public class Server {
    public static final String BASE_URI = "http://localhost:8080";

    public static org.eclipse.jetty.server.Server startServer() {
        return JettyHttpContainerFactory.createServer(
                URI.create(BASE_URI),
                getResourceConfig());
    }

    private static ResourceConfig getResourceConfig() {
        MessageRepository repository = new ChatMessageRepository();
        MessageService service = new ChatMessageService(repository);
        MessageResource messageResource = new MessageResource(service);

        return new ResourceConfig().register(messageResource);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        final org.eclipse.jetty.server.Server server = startServer();
        server.join();
    }
}
