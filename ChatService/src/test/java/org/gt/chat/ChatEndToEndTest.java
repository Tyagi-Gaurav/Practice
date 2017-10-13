package org.gt.chat;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.gt.chat.repos.ChatMessageRepository;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.resource.MessageResource;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.ChatMessageService;
import org.gt.chat.service.MessageService;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JerseyTest {
    @Override
    protected Application configure() {
        MessageRepository repository = new ChatMessageRepository();
        MessageService service = new ChatMessageService(repository);
        MessageResource messageResource = new MessageResource(service);

        return new ResourceConfig().register(messageResource);
    }

    @BeforeEach
    public void before() throws Exception {
        super.setUp();
    }

    // do not name this tearDown()
    @AfterEach
    public void after() throws Exception {
        super.tearDown();
    }

    @Test
    @DisplayName("get messages belonging to a user")
    void getMessagesForAUser() {
        //When
        Messages messages = target("message/users/1").request().get(Messages.class);

        //Then
        assertThat(messages).isNotNull();
        List<Message> messageResponseList = messages.getMessageResponseList();
        assertThat(messageResponseList).isNotEmpty();
        Message message = messageResponseList.get(0);
        assertThat(message.getContent()).isEqualTo("Hello World");
        assertThat(message.getId()).isEqualTo("2");
        assertThat(message.getTimestamp()).isEqualTo(234878234L);
    }
}
