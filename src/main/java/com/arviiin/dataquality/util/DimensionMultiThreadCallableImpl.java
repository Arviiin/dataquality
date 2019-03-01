package com.arviiin.dataquality.util;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.service.DimensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 有返回值的多线程
 */
public class DimensionMultiThreadCallableImpl implements Callable<Map<String,Object>> {//指定返回的类型

	private static Logger logger = LoggerFactory.getLogger(DimensionMultiThreadCallableImpl.class);

	private CountDownLatch endSignal;
	private DimensionBean dimensionBean;
	private Map<String,Object> resultMap;

	//在线程中为了线程安全，是防注入的，没办法，要用到这个类啊。只能从bean工厂里拿个实例了，继续往下看
	/*@Autowired
	private DimensionService dimensionService;*/

	//在线程类中写一个(无参的)构造方法，在构造方法中，通过调用工具类中的 getBean() 方法就可以拿到实例了，
	// 程序在调用这个线程类时，会自动调用其无参的构造方法，在构造方法中我们将需要的bean对象注入，然后就可以正常使用了，
	private DimensionService dimensionService;

	public DimensionMultiThreadCallableImpl(CountDownLatch endSignal, DimensionBean dimensionBean) {
		this.dimensionService = ApplicationContextProvider.getBean(DimensionService.class);
		this.endSignal = endSignal;
		this.dimensionBean = dimensionBean;
		this.resultMap = new HashMap();
	}

	@Override
	public Map<String, Object> call() throws Exception {
		// TODO Auto-generated method stub
		switch (dimensionBean.getDimensionname()){
			case "数据文件完备性":
				int expectedTotalRecordAmount = dimensionService.getExpectedTotalRecordAmount(dimensionBean);
				resultMap.put("expectedTotalRecordAmount",expectedTotalRecordAmount);
				//dimensionResultBean.setExpectedTotalRecordAmount(expectedTotalRecordAmount);
				int dataFileCompletenessResult = dimensionService.getDataFileCompletenessResult(dimensionBean);
				resultMap.put("dataFileCompletenessResult",dataFileCompletenessResult);
				//dimensionResultBean.setDataFileCompleteness(dataFileCompletenessResult);
                logger.info(expectedTotalRecordAmount+"");
                logger.info(dataFileCompletenessResult+"");
				logger.info( "数据文件完备性"+(float)dataFileCompletenessResult/expectedTotalRecordAmount+"");
				break;
			case "数据值完备性":
				int totalRecordAmountOfDataValueCompleteness = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfDataValueCompleteness",totalRecordAmountOfDataValueCompleteness);
				int dataValueCompletenessResult = dimensionService.getDataValueCompletenessResult(dimensionBean);
				resultMap.put("dataValueCompletenessResult",dataValueCompletenessResult);
				//dimensionResultBean.setDataValueCompleteness(dataValueCompletenessResult);
                logger.info(totalRecordAmountOfDataValueCompleteness+"");
                logger.info(dataValueCompletenessResult+"");
				logger.info("数据值完备性"+ (float)dataValueCompletenessResult/totalRecordAmountOfDataValueCompleteness+"");
				break;
			case "数据引用一致性":
				int totalRecordAmountOfReferentialConsistency = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfReferentialConsistency",totalRecordAmountOfReferentialConsistency);
				int referentialConsistencyResult = dimensionService.getReferentialConsistencyResult(dimensionBean);
				resultMap.put("referentialConsistencyResult",referentialConsistencyResult);
				//dimensionResultBean.setReferentialConsistency(referentialConsistencyResult);
                logger.info(totalRecordAmountOfReferentialConsistency+"");
                logger.info(referentialConsistencyResult+"");
				logger.info("数据引用一致性"+ (1 - (float)referentialConsistencyResult/totalRecordAmountOfReferentialConsistency)+"");
				break;

			case "数据格式一致性":
				int	totalRecordAmountOfFormatConsistency = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfFormatConsistency",totalRecordAmountOfFormatConsistency);
				int formatConsistencyResult = dimensionService.getFormatConsistencyResult(dimensionBean);
				resultMap.put("formatConsistencyResult",formatConsistencyResult);
				//dimensionResultBean.setFormatConsistency(formatConsistencyResult);
                logger.info(totalRecordAmountOfFormatConsistency+"");
                logger.info(formatConsistencyResult+"");
				logger.info( "数据格式一致性"+(float)formatConsistencyResult/totalRecordAmountOfFormatConsistency+"");
				break;
			case "数据记录依从性":
				int	totalRecordAmountOfDataRecordCompliance = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfDataRecordCompliance",totalRecordAmountOfDataRecordCompliance);
				int dataRecordComplianceResult = dimensionService.getDataRecordComplianceResult(dimensionBean);
				resultMap.put("dataRecordComplianceResult",dataRecordComplianceResult);
				//dimensionResultBean.setDataRecordCompliance(dataRecordComplianceResult);
                logger.info(totalRecordAmountOfDataRecordCompliance+"");
                logger.info(dataRecordComplianceResult+"");
				logger.info( "数据记录依从性"+(float)dataRecordComplianceResult/totalRecordAmountOfDataRecordCompliance+"");
				break;
			case "数据范围准确性":
				int	totalRecordAmountOfRangeAccuracy = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfRangeAccuracy",totalRecordAmountOfRangeAccuracy);
				int rangeAccuracyResult = dimensionService.getRangeAccuracyResult(dimensionBean);
				resultMap.put("rangeAccuracyResult",rangeAccuracyResult);
				//dimensionResultBean.setRangeAccuracy(rangeAccuracyResult);
                logger.info(totalRecordAmountOfRangeAccuracy+"");
                logger.info(rangeAccuracyResult+"");
				logger.info("数据范围准确性"+ (float)rangeAccuracyResult/totalRecordAmountOfRangeAccuracy+"");
				break;
			case "数据记录唯一性":
				int	totalRecordAmountOfRecordUniqueness = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfRecordUniqueness",totalRecordAmountOfRecordUniqueness);
				int recordUniquenessResult = dimensionService.getRecordUniquenessResult(dimensionBean);
				resultMap.put("recordUniquenessResult",recordUniquenessResult);
				//dimensionResultBean.setRecordUniqueness(recordUniquenessResult);
                logger.info(totalRecordAmountOfRecordUniqueness+"");
                logger.info(recordUniquenessResult+"");
				logger.info( "数据记录唯一性"+(float)recordUniquenessResult/totalRecordAmountOfRecordUniqueness+"");
				break;
			case "基于时间段的时效性":
				int	totalRecordAmountOfTimeBasedTimeliness = dimensionService.getTotalRecordAmount(dimensionBean);
				resultMap.put("totalRecordAmountOfTimeBasedTimeliness",totalRecordAmountOfTimeBasedTimeliness);
				int timeBasedTimelinessResult = dimensionService.getTimeBasedTimelinessResult(dimensionBean);
				resultMap.put("timeBasedTimelinessResult",timeBasedTimelinessResult);
				//dimensionResultBean.setTimeBasedTimeliness(timeBasedTimelinessResult);
                logger.info(totalRecordAmountOfTimeBasedTimeliness+"");
                logger.info(timeBasedTimelinessResult+"");
				logger.info("基于时间段的时效性"+ (float)timeBasedTimelinessResult/totalRecordAmountOfTimeBasedTimeliness+"");
				break;
			case "数据非脆弱性":
				int dataNonVulnerabilityResult  = dimensionService.getDataNonVulnerabilityResult(dimensionBean);
				resultMap.put("dataNonVulnerabilityResult",dataNonVulnerabilityResult);
				//dimensionResultBean.setDataNonVulnerability(dataNonVulnerabilityResult);
				logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");
				break;
			default:
				logger.error("数据错误，未找到指标名为:"+dimensionBean.getDimensionname()+"的指标");
				break;
		}

		//here we decrease the endSigle when this thread is finished
		endSignal.countDown();
		return resultMap;
	}
}
