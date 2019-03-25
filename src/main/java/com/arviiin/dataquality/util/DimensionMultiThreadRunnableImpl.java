package com.arviiin.dataquality.util;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.service.DimensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class DimensionMultiThreadRunnableImpl implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(DimensionMultiThreadRunnableImpl.class);

	private int totalRecordAmount;
	private CountDownLatch endSignal;
	private DimensionBean dimensionBean;
	private DimensionResultBean dimensionResultBean;

	//在线程中为了线程安全，是防注入的，没办法，要用到这个类啊。只能从bean工厂里拿个实例了，继续往下看
	/*@Autowired
	private DimensionService dimensionService;*/

	//在线程类中写一个(无参的)构造方法，在构造方法中，通过调用工具类中的 getBean() 方法就可以拿到实例了，
	// 程序在调用这个线程类时，会自动调用其无参的构造方法，在构造方法中我们将需要的bean对象注入，然后就可以正常使用了，
	private DimensionService dimensionService;

	public DimensionMultiThreadRunnableImpl(int totalRecordAmount, CountDownLatch endSignal, DimensionBean dimensionBean, DimensionResultBean dimensionResultBean) {
		this.dimensionService = ApplicationContextProvider.getBean(DimensionService.class);
		this.totalRecordAmount = totalRecordAmount;
		this.endSignal = endSignal;
		this.dimensionBean = dimensionBean;
		this.dimensionResultBean = dimensionResultBean;
	}

	@Override
	public void run() {
// TODO Auto-generated method stub
		switch (dimensionBean.getDimensionname()){
			case "数据文件完备性":
				int expectedTotalRecordAmount = dimensionService.getExpectedTotalRecordAmount(dimensionBean);
				dimensionResultBean.setExpectedTotalRecordAmount(expectedTotalRecordAmount);
				int dataFileCompletenessResult = dimensionService.getDataFileCompletenessResult(dimensionBean);
				dimensionResultBean.setDataFileCompleteness(dataFileCompletenessResult);
//                        logger.info(expectedTotalRecordAmount+"");
//                        logger.info(dataFileCompletenessResult+"");
				logger.info( "数据文件完备性"+(float)dataFileCompletenessResult/expectedTotalRecordAmount+"");
				break;
			case "数据值完备性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int dataValueCompletenessResult = dimensionService.getDataValueCompletenessResult(dimensionBean);
				dimensionResultBean.setDataValueCompleteness(dataValueCompletenessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataValueCompletenessResult+"");
				logger.info("数据值完备性"+ (float)dataValueCompletenessResult/totalRecordAmount+"");
				break;
			case "数据引用一致性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int referentialConsistencyResult = dimensionService.getReferentialConsistencyResult(dimensionBean);
				dimensionResultBean.setReferentialConsistency(referentialConsistencyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(referentialConsistencyResult+"");
				logger.info("数据引用一致性"+ (float)referentialConsistencyResult/totalRecordAmount+"");
				break;

			case "数据格式一致性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int formatConsistencyResult = dimensionService.getFormatConsistencyResult(dimensionBean);
				dimensionResultBean.setFormatConsistency(formatConsistencyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(formatConsistencyResult+"");
				logger.info( "数据格式一致性"+(float)formatConsistencyResult/totalRecordAmount+"");
				break;
			case "数据记录依从性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int dataRecordComplianceResult = dimensionService.getDataRecordComplianceResult(dimensionBean);
				dimensionResultBean.setDataRecordCompliance(dataRecordComplianceResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataRecordComplianceResult+"");
				logger.info( "数据记录依从性"+(float)dataRecordComplianceResult/totalRecordAmount+"");
				break;
			case "数据范围准确性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int rangeAccuracyResult = dimensionService.getRangeAccuracyResult(dimensionBean);
				dimensionResultBean.setRangeAccuracy(rangeAccuracyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(rangeAccuracyResult+"");
				logger.info("数据范围准确性"+ (float)rangeAccuracyResult/totalRecordAmount+"");
				break;
			case "数据记录唯一性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int recordUniquenessResult = dimensionService.getRecordUniquenessResult(dimensionBean);
				dimensionResultBean.setRecordUniqueness(recordUniquenessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(recordUniquenessResult+"");
				logger.info( "数据记录唯一性"+(float)recordUniquenessResult/totalRecordAmount+"");
				break;
			case "基于时间段的时效性":
				if (totalRecordAmount == -1){
					totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
					dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
				}
				int timeBasedTimelinessResult = dimensionService.getTimeBasedTimelinessResult(dimensionBean);
				dimensionResultBean.setTimeBasedTimeliness(timeBasedTimelinessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(timeBasedTimelinessResult+"");
				logger.info("基于时间段的时效性"+ (float)timeBasedTimelinessResult/totalRecordAmount+"");
				break;
			case "数据非脆弱性":
				int dataNonVulnerabilityResult  = dimensionService.getDataNonVulnerabilityResult(dimensionBean);
				dimensionResultBean.setDataNonVulnerability(dataNonVulnerabilityResult);
				logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");
				break;
			default:
				logger.error("数据错误，未找到指标名为:"+dimensionBean.getDimensionname()+"的指标");
				break;
		}
		//here we decrease the endSigle when this thread is finished
		endSignal.countDown();
	}
}
