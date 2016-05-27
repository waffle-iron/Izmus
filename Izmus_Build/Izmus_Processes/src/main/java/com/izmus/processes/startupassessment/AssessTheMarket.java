package com.izmus.processes.startupassessment;

import java.util.HashSet;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.Measurement;
import com.izmus.data.domain.startups.MeasurementQuestion;
import com.izmus.data.domain.startups.StartupScoreCard;
import com.izmus.data.repository.IStartupScoreCardRepository;

@Component("AssessTheMarket")
public class AssessTheMarket {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(AssessTheMarket.class);
	private static final String MARKET_MESSAGE = "navBar.menu.startupAssessment.measurements.market.";
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IStartupScoreCardRepository startupScoreCardRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		try {
			LOGGER.info("Adding Assess The Market Measurement");
			Integer scoreCardId = (Integer) runtimeService.getVariable(execution.getId(), "scoreCardId");
			StartupScoreCard scoreCard = startupScoreCardRepository.findOne(scoreCardId);
			Measurement marketMeasurement = new Measurement();
			marketMeasurement.setFinalScoreRatio(0.20);
			marketMeasurement.setScoreCard(scoreCard);
			if (scoreCard.getMeasurements() == null){
				scoreCard.setMeasurements(new HashSet<Measurement>());
			}
			scoreCard.getMeasurements().add(marketMeasurement);
			setupMeasurement(marketMeasurement, MARKET_MESSAGE);
			startupScoreCardRepository.save(scoreCard);
			LOGGER.info("Assess The Market Measurement Saved");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void setupMeasurement(Measurement competitionMeasurement, String message) {
		competitionMeasurement.setTitleLocale(message + "title");
		competitionMeasurement.setTitle(messageSource.getMessage(competitionMeasurement.getTitleLocale(), null, LocaleContextHolder.getLocale()));
		competitionMeasurement.setDescriptionLocale(message + "description");
		competitionMeasurement.setDescription(messageSource.getMessage(competitionMeasurement.getDescriptionLocale(), null, LocaleContextHolder.getLocale()));
		addMeasurementQuestions(competitionMeasurement, message);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addMeasurementQuestions(Measurement measurement, String message) {
		measurement.setMeasurementQuestions(new HashSet<MeasurementQuestion>());
		int i = 0;
		while (true){
			try {
				MeasurementQuestion question = new MeasurementQuestion();
				question.setQuestion(messageSource.getMessage(message + "questions." + i, null, LocaleContextHolder.getLocale()));
				question.setQuestionLocale(message + "questions." + i);
				question.setMeasurement(measurement);
				measurement.getMeasurementQuestions().add(question);
				i++;
			} catch (NoSuchMessageException e) {
				if (measurement.getMeasurementQuestions().size() <= 0){
					LOGGER.error("No Questions For Measurement: " + measurement);
				}
				break;
			}
		}
	}
}
