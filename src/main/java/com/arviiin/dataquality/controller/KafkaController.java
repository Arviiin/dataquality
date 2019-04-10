package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.service.EmailService;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class KafkaController {
 
	/**
	 * 注入kafkaTemplate
	 */
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EvaluationRelatedService evaluationRelatedService;
 
	/**
	 * 发送消息的方法
	 * @param key  推送数据的key
	 * @param data 推送数据的data
	 */
	private void send(String key, String data) {
		// topic 名称 key data 消息数据
		kafkaTemplate.send("my_test", key, data);
	}
 
	//test 主题 1 my_test 3
	@GetMapping("/kafka")
	public String testKafka() {
		int iMax = 6;
		for (int i = 1; i < iMax; i++) {
			send("key" + i, "data" + i);
		}
		return "success";
	}
 
	/**
	 * 消费者使用日志打印消息
	 */
	@KafkaListener(topics = "my_test")
	public void receive(ConsumerRecord<?, ?> consumer) {
		System.out.println("topic名称:" + consumer.topic() 
            	+ ",key:" + consumer.key()
				+ ",分区位置:" + consumer.partition()
				+ ",下标" + consumer.offset());
	}

	/**
	 * 消费者监听topic，进行相应的处理
	 * 监听email主题,有消息就读取
	 * topic名称:email,key:emailSignal,value:true,分区位置:0, 下标0
	 */
	@KafkaListener(topics = "email")
	public void receiveEmailSignal(ConsumerRecord<?, ?> consumer) {
		System.out.println("topic名称:" + consumer.topic()
						    + ",key:" + consumer.key()
							+ ",value:" + consumer.value()
							+ ",分区位置:" + consumer.partition()
							+ ", 下标" + consumer.offset());
		if ("emailSignal".equals(consumer.key()) && "true".equals(consumer.value())){
			//如果接收到队列的消息，则表示评估计算已经完成，开启新线程生成评估报告以邮件的形式发送给用户
			emailService.sendEmail();
			// 2.将发送邮件的记录保存到数据库中
			Map<String, Object> latestEvaluationInitData = evaluationRelatedService.getLatestEvaluationInitData();
			emailService.saveRecordOfSendEmail(latestEvaluationInitData);
		}
	}
 
}
