package com.izmus.api.processes;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.image.util.ReflectUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.api.processes.ProcessData;

@RestController
@RequestMapping("api/Processes")
public class ProcessService {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessService.class);
	@Autowired
	private RepositoryService repositoryService;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Processes', '')")
	public List<ProcessData> getProcesses(){
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
				.orderByProcessDefinitionId().asc().latestVersion().list();
		List<ProcessData> returnList = new ArrayList<ProcessData>();
		for (ProcessDefinition process : processDefinitionList){
			ProcessData processData = new ProcessData();
			processData.setId(process.getId());
			processData.setName(process.getName());
			processData.setBase64Image("data:image/jpg;base64," + getBase64ImageString(process.getId()));
			returnList.add(processData);
		}
		return returnList;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String getBase64ImageString(String processId) {
		String base64String = "";
		try {
			InputStream imageStream = getProcessImageInputStream(processId);
			byte[] imageByteArray = IOUtils.toByteArray(imageStream);
			base64String = Base64.encodeBase64String(imageByteArray);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return base64String;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/ProcessImage", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Processes', '')")
	public ResponseEntity<byte[]> getProcessImage(@RequestParam(value = "processId") String processId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		InputStream imageStream = null;
		ResponseEntity<byte[]> response = null;
		try {
			imageStream = getProcessImageInputStream(processId);
			response = new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return response;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private InputStream getProcessImageInputStream(String processId){
		InputStream imageStream = null;
		try {
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processId).singleResult();
			BpmnModel model = repositoryService.getBpmnModel(processDefinition.getId());
			DefaultProcessDiagramGenerator diagramGenerator = new CustomProcessDiagramGenerator();
			imageStream = diagramGenerator.generateJpgDiagram(model);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return imageStream;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private class CustomProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
		/*----------------------------------------------------------------------------------------------------*/
		@Override
		public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
				List<String> highLightedFlows, String activityFontName, String labelFontName,
				ClassLoader customClassLoader, double scaleFactor) {
			prepareBpmnModel(bpmnModel);
			DefaultProcessDiagramCanvas processDiagramCanvas = newInitProcessDiagramCanvas(bpmnModel, imageType,
					activityFontName, labelFontName, customClassLoader);
			// Draw pool shape, if process is participant in collaboration
			for (Pool pool : bpmnModel.getPools()) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
				processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
			}
			// Draw lanes
			for (Process process : bpmnModel.getProcesses()) {
				for (Lane lane : process.getLanes()) {
					GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
					processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
				}
			}
			// Draw activities and their sequence-flows
			for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)) {
				drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows,
						scaleFactor);
			}
			// Draw artifacts
			for (Process process : bpmnModel.getProcesses()) {
				for (Artifact artifact : process.getArtifacts()) {
					drawArtifact(processDiagramCanvas, bpmnModel, artifact);
				}
			}
			return processDiagramCanvas.generateImage(imageType);
		}
		/*----------------------------------------------------------------------------------------------------*/
		private DefaultProcessDiagramCanvas newInitProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
				String activityFontName, String labelFontName, ClassLoader customClassLoader) {
			// We need to calculate maximum values to know how big the image
			// will be in its entirety
			double minX = Double.MAX_VALUE;
			double maxX = 0;
			double minY = Double.MAX_VALUE;
			double maxY = 0;
			for (Pool pool : bpmnModel.getPools()) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
				minX = graphicInfo.getX();
				maxX = graphicInfo.getX() + graphicInfo.getWidth();
				minY = graphicInfo.getY();
				maxY = graphicInfo.getY() + graphicInfo.getHeight();
			}
			List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
			for (FlowNode flowNode : flowNodes) {
				GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				// width
				if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
					maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
				}
				if (flowNodeGraphicInfo.getX() < minX) {
					minX = flowNodeGraphicInfo.getX();
				}
				// height
				if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
					maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
				}
				if (flowNodeGraphicInfo.getY() < minY) {
					minY = flowNodeGraphicInfo.getY();
				}
				for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
					List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
					if (graphicInfoList != null) {
						for (GraphicInfo graphicInfo : graphicInfoList) {
							// width
							if (graphicInfo.getX() > maxX) {
								maxX = graphicInfo.getX();
							}
							if (graphicInfo.getX() < minX) {
								minX = graphicInfo.getX();
							}
							// height
							if (graphicInfo.getY() > maxY) {
								maxY = graphicInfo.getY();
							}
							if (graphicInfo.getY() < minY) {
								minY = graphicInfo.getY();
							}
						}
					}
				}
			}
			List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
			for (Artifact artifact : artifacts) {
				GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());
				if (artifactGraphicInfo != null) {
					// width
					if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
						maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
					}
					if (artifactGraphicInfo.getX() < minX) {
						minX = artifactGraphicInfo.getX();
					}
					// height
					if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY) {
						maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
					}
					if (artifactGraphicInfo.getY() < minY) {
						minY = artifactGraphicInfo.getY();
					}
				}
				List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
				if (graphicInfoList != null) {
					for (GraphicInfo graphicInfo : graphicInfoList) {
						// width
						if (graphicInfo.getX() > maxX) {
							maxX = graphicInfo.getX();
						}
						if (graphicInfo.getX() < minX) {
							minX = graphicInfo.getX();
						}
						// height
						if (graphicInfo.getY() > maxY) {
							maxY = graphicInfo.getY();
						}
						if (graphicInfo.getY() < minY) {
							minY = graphicInfo.getY();
						}
					}
				}
			}
			int nrOfLanes = 0;
			for (Process process : bpmnModel.getProcesses()) {
				for (Lane l : process.getLanes()) {
					nrOfLanes++;
					GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
					// // width
					if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
						maxX = graphicInfo.getX() + graphicInfo.getWidth();
					}
					if (graphicInfo.getX() < minX) {
						minX = graphicInfo.getX();
					}
					// height
					if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
						maxY = graphicInfo.getY() + graphicInfo.getHeight();
					}
					if (graphicInfo.getY() < minY) {
						minY = graphicInfo.getY();
					}
				}
			}
			// Special case, see https://activiti.atlassian.net/browse/ACT-1431
			if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty() && nrOfLanes == 0) {
				// Nothing to show
				minX = 0;
				minY = 0;
			}
			return new CustomProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY, imageType,
					activityFontName, labelFontName, customClassLoader);
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas {
		/*----------------------------------------------------------------------------------------------------*/
		public CustomProcessDiagramCanvas(int i, int j, int minX, int minY, String imageType, String activityFontName,
				String labelFontName, ClassLoader customClassLoader) {
			super(i, j, minX, minY, imageType, activityFontName, labelFontName, customClassLoader);
		}
		/*----------------------------------------------------------------------------------------------------*/
		@Override
		public void initialize(String imageType) {
			if ("png".equalsIgnoreCase(imageType)) {
				this.processDiagram = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
			} else {
				this.processDiagram = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
			}
			this.g = processDiagram.createGraphics();
			if ("png".equalsIgnoreCase(imageType) == false) {
				this.g.setBackground(new Color(255, 255, 255, 0));
				this.g.clearRect(0, 0, canvasWidth, canvasHeight);
			}
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setPaint(new Color(60, 90, 120));
			Font font = new Font(activityFontName, Font.BOLD, 9);
			g.setFont(font);
			this.fontMetrics = g.getFontMetrics();
			LABEL_FONT = new Font(labelFontName, Font.ITALIC, 9);
			try {
				USERTASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/userTask.png", customClassLoader));
				SCRIPTTASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/scriptTask.png", customClassLoader));
				SERVICETASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/serviceTask.png", customClassLoader));
				RECEIVETASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/receiveTask.png", customClassLoader));
				SENDTASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/sendTask.png", customClassLoader));
				MANUALTASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/manualTask.png", customClassLoader));
				BUSINESS_RULE_TASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/businessRuleTask.png", customClassLoader));
				SHELL_TASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/shellTask.png", customClassLoader));
				CAMEL_TASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/camelTask.png", customClassLoader));
				MULE_TASK_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/muleTask.png", customClassLoader));
				TIMER_IMAGE = ImageIO.read(ReflectUtil.getResource("org/activiti/icons/timer.png", customClassLoader));
				COMPENSATE_THROW_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/compensate-throw.png", customClassLoader));
				COMPENSATE_CATCH_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/compensate.png", customClassLoader));
				ERROR_THROW_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/error-throw.png", customClassLoader));
				ERROR_CATCH_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/error.png", customClassLoader));
				MESSAGE_THROW_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/message-throw.png", customClassLoader));
				MESSAGE_CATCH_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/message.png", customClassLoader));
				SIGNAL_THROW_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/signal-throw.png", customClassLoader));
				SIGNAL_CATCH_IMAGE = ImageIO
						.read(ReflectUtil.getResource("org/activiti/icons/signal.png", customClassLoader));
			} catch (IOException e) {
				ProcessService.LOGGER.error("Could not load image for process diagram creation: {}", e.getMessage());
			}
		}
	}
}
