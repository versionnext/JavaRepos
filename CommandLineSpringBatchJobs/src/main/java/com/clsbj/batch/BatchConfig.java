package com.clsbj.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.RowMapper;

import com.clsbj.batch.model.User;
import com.clsbj.batch.service.UserProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	Logger logger = LoggerFactory.getLogger(BatchConfig.class);

	@Value("${chunkSize:10}")
	private int chunkSize;

	@Value("${maxThreads:4}")
	private int maxThreads;

	@Value("${user.file.out.path}")
	private String userOutPath;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private UserProcessor userProcessor;

	@Autowired
	private DataSource dataSource;

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(maxThreads);
		return taskExecutor;
	}

	@Bean
	public Job userFileWithoutProcessorJob() {
		logger.info("start Job : userFileWithoutProcessorJob");
		return jobBuilderFactory.get("userFileWithoutProcessorJob").incrementer(new RunIdIncrementer())
				.start(userFileWithoutProcessorStep()).build();
	}

	@Bean
	public Job userFileWithProcessorJob() {
		logger.info("start Job : userFileWithProcessorJob");
		return jobBuilderFactory.get("userFileWithProcessorJob").incrementer(new RunIdIncrementer())
				.start(userFileWithProcessorStep()).build();
	}

	@Bean
	public ItemProcessor<User, User> userItemProcessor() {
		return userProcessor;
	}

	@Bean
	public Step userFileWithoutProcessorStep() {

		JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
		try {
			final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
			sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
			sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
			sqlPagingQueryProviderFactoryBean.setFromClause("from users");
			sqlPagingQueryProviderFactoryBean.setSortKey("user_id");
			reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
			reader.setDataSource(dataSource);
			reader.setPageSize(chunkSize);
			reader.setRowMapper(UserRowMapper());
			reader.afterPropertiesSet();
			reader.setSaveState(true);
		} catch (Exception e) {
			logger.error("Error occurred during Item reader" + e.getMessage());
		}

		return stepBuilderFactory.get("monitorFileCreatestep").<User, User>chunk(chunkSize).reader(reader)
				.writer(userItemWriter()).build();

	}

	@Bean
	public Step userFileWithProcessorStep() {

		JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
		try {
			final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
			sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
			sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
			sqlPagingQueryProviderFactoryBean.setFromClause("from users");
			sqlPagingQueryProviderFactoryBean.setSortKey("user_id");
			reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
			reader.setDataSource(dataSource);
			reader.setPageSize(chunkSize);
			reader.setRowMapper(UserRowMapper());
			reader.afterPropertiesSet();
			reader.setSaveState(true);
		} catch (Exception e) {
			logger.error("Error occurred during Item reader" + e.getMessage());
		}

		return stepBuilderFactory.get("userFileCreatestep").<User, User>chunk(chunkSize).reader(reader)
				.processor(userProcessor).writer(userItemWriter()).build();

	}

	private RowMapper<User> UserRowMapper() {
		return new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User User = new User();
				User.setUserId(rs.getString("user_id"));
				User.setLoginName(rs.getString("login_name"));
				User.setUserName(rs.getString("login_name"));
				User.setRoleName(rs.getString("user_role"));

				return User;
			}
		};
	}

	@Bean
	public FlatFileItemWriter<User> userItemWriter() {
		// Create writer instance
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();

		writer.setResource(new FileSystemResource(userOutPath));

		writer.setLineAggregator(new DelimitedLineAggregator<User>() {
			{
				setDelimiter(";");
				setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
					{
						setNames(new String[] { "userId", "loginName", "userName", "roleName" });

					}
				});
			}
		});
		return writer;
	}

}
