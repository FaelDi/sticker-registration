package br.com.d2.figurinha.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFigutinhasAlbumProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void send(String message) {
		log.info("Mensagem enviada: {" + message + "}");
		String topicName = "ALBUM_CRIADO";
		kafkaTemplate.send(topicName, message);
	}

}
