package com.eyes.eyesspace.async.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eyesYeager
 * date 2023/2/9 16:17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportModel implements Serializable {
	private String subject;

	private Long visitNum;

	private Long visitorNum;

	private Integer commentNum;

	private Integer leaveMsgNum;
}
