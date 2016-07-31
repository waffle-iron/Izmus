package com.izmus.processes.analysisrequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupAbstract;
import com.izmus.data.repository.IStartupAbstractRepository;

@Component("MoveToAssessorsDashboardService")
public class MoveToAssessorsDashboardService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(MoveToAssessorsDashboardService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IStartupAbstractRepository startupAbstractRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) throws Exception{
		try {
			Integer startupId = (Integer) runtimeService.getVariable(execution.getId(), "startupId");
			StartupAbstract startup = startupAbstractRepository.findDistinctStartupAbstractByStartupId(startupId);
			if (startup instanceof AvailableStartup){
				startup = convertAvailableStartup((AvailableStartup) startup);
				runtimeService.setVariable(execution.getId(), "startupId", startup.getStartupId());
			}
		} catch (Exception e) {
			LOGGER.debug("Failed to send out email to Admins for analysis request\r\n" + e.getMessage());
			throw(e);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private StartupAbstract convertAvailableStartup(AvailableStartup startup) {
		Startup newStartup = new Startup();
		newStartup.setFundingStage(startup.getFundingStage());
		newStartup.setFounded(startup.getFounded());
		newStartup.setStartupName(startup.getStartupName());
		newStartup.setMiscellaneous(startup.getMiscellaneous());
		newStartup.setLogo(startup.getLogo());
		newStartup.setNumberOfEmployees(startup.getNumberOfEmployees());
		newStartup.setProductStage(startup.getProductStage());
		newStartup.setSector(startup.getSector());
		newStartup.setSite(startup.getSite());
		newStartup.setStartupId(startup.getStartupId());
		startupAbstractRepository.delete(startup);
		newStartup = startupAbstractRepository.save(newStartup);
		return newStartup;
	}
}
