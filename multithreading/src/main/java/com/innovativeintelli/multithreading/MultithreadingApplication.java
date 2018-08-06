package com.innovativeintelli.multithreading;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.innovativeintelli.multithreading.jpa.EmployeeRepository;
import com.innovativeintelli.multithreading.model.Employee;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@SpringBootApplication
public class MultithreadingApplication {
	
	private static final Logger log = LoggerFactory.getLogger(MultithreadingApplication.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ResourceLoader loader;
	
	@Autowired
	TaskExecutor taskExecutor;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	private String outputFolder =null;

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingApplication.class, args);
	}
	
	
/*Enable the below code to execute the reports sequentially	
 * @Bean
	public CommandLineRunner demo(EmployeeRepository repository) {
		return (args) -> {
			Connection con = jdbcTemplate.getDataSource().getConnection();
			Resource resource = loader.getResource("classpath:templates/EmployeeSalary.jasper");
			InputStream is = resource.getInputStream();
			JasperReport jr = (JasperReport) JRLoader.loadObject(is);
		    outputFolder = "/Users/amitheshmerugu/output/{emp_no}.pdf";
			List<Employee> employees = repository.findAll();
			LocalTime startTime = LocalTime.now();
			List<Integer> empNos = employees.stream().map(x -> x.getEmpNo()).limit(100).collect(Collectors.toList());
			log.info("Report generation without multithreading started at "+startTime);
			empNos.forEach(x -> {
				log.info("Report is generated for employeee with ID "+x);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("emp_no", x); 
			String filename = outputFolder.replace("{emp_no}", String.valueOf(x));
			try {
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
					System.out.println("Created file: " + filename);
					Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error in creating file with emp_no "+x);
			} 
			 });
			LocalTime endTime = LocalTime.now();
			log.info("Report generation without multithreading ended at "+endTime);
			log.info("Total time taken without Multithreading: "+(ChronoUnit.SECONDS.between(startTime,endTime)));
			con.close();
		};
	}*/
}

