package br.com.d2.figurinha.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.d2.figurinha.kafka.producer.CreateFigutinhasAlbumProducer;
import br.com.d2.figurinha.model.entity.dto.ColecaoDTO;
import br.com.d2.figurinha.service.ColecaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFigutinhasAlbumConsumer {

	@Autowired
	private ColecaoService colecaoService;

	@Autowired
	private CreateFigutinhasAlbumProducer createFigutinhasAlbumProducer;

	private final String topicName = "ALBUM_CRIADO";

	@KafkaListener(topics = "ALBUM_CRIADO", groupId = "group_id")
	public void consume(ConsumerRecord<String, String> payload) {
		log.info("Tópico: {}", topicName);
		log.info("key: {}", payload.key());
		log.info("Headers: {}", payload.headers());
		log.info("Partion: {}", payload.partition());
		log.info("ID Album: {}", payload.value());
		
		String statusCriacao = "OK";
		String idAlbum = payload.value();
		ColecaoDTO colecao = new ColecaoDTO(idAlbum);
		
		try {
			colecaoService.criarColecao(colecao);
		} catch (Exception e) {
			statusCriacao = "NOK";
			log.error("Erro ao criar figurinhas para o album ID: ".concat(idAlbum), e);
		}

		createFigutinhasAlbumProducer.send(idAlbum.concat(statusCriacao));
	}

}
