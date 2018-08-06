package com.innovativeintelli.multithreading.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.innovativeintelli.multithreading.jpa.EmployeeRepository;
import com.innovativeintelli.multithreading.model.Employee;
import com.innovativeintelli.multithreading.util.AppConstant;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class ThreadExecutorService {
	
	private static final Logger log = LoggerFactory.getLogger(ThreadExecutorService.class);
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ResourceLoader loader;
	
	@Value("${number.of.threads}")
	private  Integer numberOfThreads;
	
	private Connection con = null;
	
	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1000000);

	public void generateReportsAsync() throws ServiceException{
		LocalTime startTime = null;
		try {
		log.info("->>>>>>>>>>>>>>>>>>>>Total Number of Threads -->>>>>>>>>>>>>>>>"+numberOfThreads);	
		populateEmployeeNos();
		Resource resource = loader.getResource("classpath:templates/EmployeeSalary.jasper");
		InputStream is = resource.getInputStream();
		JasperReport jr = (JasperReport) JRLoader.loadObject(is);
		con = jdbcTemplate.getDataSource().getConnection();
	    startTime = LocalTime.now();
		log.info("Report generation with multithreading started at "+startTime);
		for (int i=0;i<numberOfThreads;i++) {
			taskExecutor.execute(() -> {
				try {
				while (!queue.isEmpty()) {
					Integer empNo = queue.take();
					log.info("Processing Empolyee with id "+empNo);
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("emp_no", empNo); 
					String filename = AppConstant.REPORT_PDF_NAME.replace("{emp_no}", String.valueOf(empNo));
					JasperPrint print = JasperFillManager.fillReport(
							jr,
							data,
							con);
					ExporterInput ei = new SimpleExporterInput(print);
					OutputStreamExporterOutput eo = new SimpleOutputStreamExporterOutput(filename);
					JRPdfExporter pdfExporter = new JRPdfExporter();
					pdfExporter.setExporterInput(ei);
					pdfExporter.setExporterOutput(eo);
					pdfExporter.exportReport();
					log.info("Finished Empolyee with id "+empNo);
					Thread.sleep(1000);
				}
				}catch(InterruptedException e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				} catch (JRException e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
			});
		}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		} catch (JRException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		LocalTime endTime = LocalTime.now();
		log.info("Report generation with multithreading ended at "+endTime);
		log.info("Time taken with Multithreading "+(ChronoUnit.SECONDS.between(startTime,endTime)));
	}

	private void populateEmployeeNos() {
		log.info("populateEmployeeNos Begin");
		List<Employee> employees = null;
		employees = employeeRepository.findAll().stream().limit(100).collect(Collectors.toList());
		employees.forEach(x -> {
			try {
				queue.put(x.getEmpNo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		log.info("populateEmployeeNos End");
	}
}