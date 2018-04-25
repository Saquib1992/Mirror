package me.saquib.mirror;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
public class MirrorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirrorApplication.class, args);
	}
}


@Controller
class SocketController{
	@MessageMapping("/mirror")
    @SendTo("/topic/mirror")
    public Draw mirror(Draw message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Draw();
    }
}

class Draw{
	private String currX;
	private String currY;
	private String prevX;
	private String prevY;
	private String color;
	public String getCurrX() {
		return currX;
	}
	public void setCurrX(String currX) {
		this.currX = currX;
	}
	public String getCurrY() {
		return currY;
	}
	public void setCurrY(String currY) {
		this.currY = currY;
	}
	public String getPrevX() {
		return prevX;
	}
	public void setPrevX(String prevX) {
		this.prevX = prevX;
	}
	public String getPrevY() {
		return prevY;
	}
	public void setPrevY(String prevY) {
		this.prevY = prevY;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}



@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/mirror");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/mirror-app").withSockJS();
    }

}